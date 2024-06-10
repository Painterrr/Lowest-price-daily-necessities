package com.fisa.lep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAPIService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${kakao.api.key}")
    private String KAKAO_API_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 마트 이름으로 도로명 주소 반환
     * @param martName
     * @return 도로명 주소(address name. 받을 떄는 fullAddr
     */
    public String getFullAddressFromKakaoApi(String martName) {
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + martName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();

            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode documents = root.path("documents");

            if (documents.isArray() && documents.size() > 0) {
                JsonNode addressNode = documents.get(0).path("address_name");
                return addressNode.asText();
            } else {
                log.warn("No address found for mart: " + martName);
                return null;
            }
        } catch (RestClientException | JsonProcessingException e) {
            log.error("Error calling Kakao API", e);
            return null;
        }
    }

    /**
     *
     * @param address
     * @return 행정동 코드(String)
     */
    public String getAdminDistrictCodeFromAddress(String address) {
        double[] coordinates = getCoordinatesFromAddress(address);
        if (coordinates != null && coordinates.length == 2) {
            String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x={x}&y={y}";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(
                        apiUrl, HttpMethod.GET, entity, String.class, coordinates[0], coordinates[1]);

                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode documents = root.path("documents");

                log.info("** in getAdminDistrictCodeFromAddress try");

                if (documents.isArray() && !documents.isEmpty()) {
                    log.info("** document has elements");

                    // documents 배열에서 "region_type": "H"인 document 찾기
                    JsonNode targetDocument = null;
                    for (JsonNode document : documents) {
                        if (document.path("region_type").asText().equals("H")) {
                            targetDocument = document;
                            break;
                        }
                    }

                    if (targetDocument != null) {
                        String code = targetDocument.path("code").asText();
                        log.info("** code: {}", code);
                        return code;
                    } else {
                        log.warn("No document found with region_type 'H' for address: " + address);
                        return null;
                    }
                } else {
                    log.warn("No admin district found for address: " + address);
                    return null;
                }
            } catch (RestClientException | IOException e) {
                log.error("Error calling Kakao API", e);
                return null;
            }
        } else {
            log.warn("Failed to get coordinates for address: " + address);
            return null;
        }
    }


    /**
     *
     * @param address
     * @return x, y
     * 마트 주소에 해당하는 좌표 반환
     */
    private double[] getCoordinatesFromAddress(String address) {
        try {
            String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query={address}";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class, address);
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode documents = root.path("documents");

            if (documents.isArray() && !documents.isEmpty()) {
                JsonNode coordinatesNode = documents.get(0).path("address");
                double[] coordinates = new double[2];
                coordinates[0] = coordinatesNode.get("x").asDouble();
                coordinates[1] = coordinatesNode.get("y").asDouble();
                log.info("** x: {}", coordinates[0]);
                log.info("** y: {}", coordinates[1]);
                return coordinates;
            } else {
                log.warn("No coordinates found for address: " + address);
                return null;
            }
        } catch (RestClientException | IOException e) {
            log.error("Error calling Kakao API", e);
            return null;
        }
    }
}
