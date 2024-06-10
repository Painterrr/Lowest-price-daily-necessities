package com.fisa.lep.controller;

import com.fisa.lep.service.MartService;
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
public class MartController {

    private final MartService martService;

    @PostMapping("/api/upload-marts")
    public ResponseEntity<String> uploadMarts(@RequestParam("file") MultipartFile file) {
        martService.saveMartsFromCsv(file);
        return ResponseEntity.ok("File uploaded and data saved successfully.");
    }
}
