
package com.toyproject.crave.controller;
import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.crave.service.ConfigService;
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
    public enum EScope{
        PERSONONLY,
        ALL
    }
    public enum EAlgorithmType {
        DFS,
        BFS
    }
    public enum EStepType {
        FRONTSTEP,
        BACKSTEP
    }

    public enum EMethodType {
        FRONT,
        FRONTANDBACK,
        BACK
    }

    public enum EHowType {
        EXACT,
        MINIMUM,
        MAXIMUM,
        NONE
    }
    static ConfigDTO config;

    public ConfigController (){
        config = new ConfigDTO();
    }
    public ResponseEntity<String> ip (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        System.out.println(request.getRemoteAddr());
        System.out.println("fsdfdsf");
        return ResponseEntity.ok(request.getRemoteAddr());
    }

    public static int getMethod(){
        return config.getMethod();
    }

    public static String getDestination(){
        return config.getDestination();
    }

    public static int getStage() {return config.getNumStage();}

    @PutMapping("/main")
    public ResponseEntity<Integer> ia (HttpServletRequest request) {


        int ret = -1;

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(requestBody);

            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(requestBody, ConfigDTO.class);

            if (config.areMembersNotNull()){
                ConfigService configService = new ConfigService();
                ret = configService.startNamuMediator(config);
                if (ret != -2){
                    configService.startNamuCenter(config);
                }
            }
            else {
                ret = -2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ret);
    }
}