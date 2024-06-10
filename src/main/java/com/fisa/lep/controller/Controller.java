package com.fisa.lep.controller;

import com.fisa.lep.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {

    private Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/api/{area}/{product}")
    public ResponseEntity<String> read (@PathVariable String area, @PathVariable String product) {
        String lowest = service.read(area, product);

        log.info("** in controller, lowest: {}", lowest);

        return ResponseEntity.ok(lowest);
    }
}
