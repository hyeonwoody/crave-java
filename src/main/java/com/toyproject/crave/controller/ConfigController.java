
package com.toyproject.crave.controller;
import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/config")
public class ConfigController {

    public ResponseEntity<String> ip (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        System.out.println(request.getRemoteAddr());
        System.out.println("fsdfdsf");
        return ResponseEntity.ok(request.getRemoteAddr());
    }

    @PutMapping("/main")
    public ResponseEntity<String> ia (HttpServletRequest request) {


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(requestBody);

            ObjectMapper objectMapper = new ObjectMapper();
            ConfigDTO config = objectMapper.readValue(requestBody, ConfigDTO.class);

            System.out.println(config.getHow());



        } catch (IOException e) {
            e.printStackTrace();

        }
        return ResponseEntity.ok(request.getRemoteAddr());
    }
}