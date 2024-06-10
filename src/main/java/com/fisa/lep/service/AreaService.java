package com.fisa.lep.service;

import com.fisa.lep.area.entity.Area;
import com.fisa.lep.repository.AreaRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AreaService {

    private final AreaRepository areaRepository;

    public void saveAreasFromCsv(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = reader.readAll();
            records.stream()
                .skip(1) // Skip the header row
                .forEach(record -> {
                    if (record[3] != null) {
                        String hCode = record[0];
                        String fullAddr = String.join(" ", record[1], record[2], record[3]);
                        log.info("** hCode: {}, fullAddr: {}", hCode, fullAddr);

                        Area area = new Area();
                        area.setHCode(hCode);
                        area.setFullAddr(fullAddr);
                        areaRepository.save(area);
                    } else {

                    }
                });
        } catch (IOException | CsvException e) {
            log.error("Error reading CSV file", e);
        }
    }
}
