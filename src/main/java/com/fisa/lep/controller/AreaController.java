package com.fisa.lep.controller;

import com.fisa.lep.service.AreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AreaController {

    private final AreaService areaService;

    @PostMapping("/api/upload-areas")
    public ResponseEntity<String> uploadAreas(@RequestParam("file") MultipartFile file) {
        areaService.saveAreasFromCsv(file);
        return ResponseEntity.ok("File uploaded and data saved successfully.");
    }
}