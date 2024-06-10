package com.fisa.lep.service;

import com.fisa.lep.area.entity.Area;
import com.fisa.lep.repository.AreaRepository;
import com.fisa.lep.mart.entity.Mart;
import com.fisa.lep.repository.MartRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MartService {

    private final MartRepository martRepository;
    private final AreaRepository areaRepository;
    private final KakaoAPIService kakaoAPIService;

    /**
     * csv 파일 읽음
     * processRecord에서 필요한 데이터 찾고, 구해서 DB에 저장
     * @param file
     */
    public void saveMartsFromCsv(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String[] headers = reader.readNext();
            String[] record;

            while ((record = reader.readNext()) != null) {
                processRecord(record);
            }
        } catch (IOException | CsvException e) {
            log.error("Error reading CSV file", e);
        }
    }

    /**
     * 파일 레코드에서 판매업소 찾기
     * 판매업소로 도로명 주소 찾기
     * 로도명 주소로 행정동 코드 얻기
     * 정동 코드로 areaId 찾기
     * Mart 객체 생성해서 DB에 저장
     * @param record
     */
    private void processRecord(String[] record) {
        String martName = record[3];
        String brand = record[4];
        Optional<Mart> martOptional = martRepository.findByMartNameAndBrand(martName, brand);

        if (martOptional.isEmpty()) {

            // 도로명 주소를 가져오기
            String fullAddr = kakaoAPIService.getFullAddressFromKakaoApi(martName);
            // 도로명 주소로 행정동 코드 얻기
            String hCode = kakaoAPIService.getAdminDistrictCodeFromAddress(fullAddr);

            log.info("** fullAddr(도로명 주소): {}", fullAddr);
            log.info("** h_code(행정동 코드): {}", hCode);

            // hCode 이용하여 area를 찾음
            try {
                Area area = areaRepository.findByhCode(hCode);
                log.info("** 행정동 주소로 찾은 area Id(받아온 데이터가 부합하는지 확인): {}, ", area.getId());

                // Mart 엔티티 생성 및 저장
                Mart mart = new Mart();
                mart.setMartName(martName);
                mart.setBrand(brand);
                mart.setAreaId(area);
                martRepository.save(mart);
            } catch (NullPointerException e) {
                log.info("** Target area not found.");
            }

        } else {
            log.warn("Mart with name {} already exists", martName);
        }
    }

}
