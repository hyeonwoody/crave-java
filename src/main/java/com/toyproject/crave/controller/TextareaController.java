package com.toyproject.crave.controller;

import libs.sse.CustomSseEmitter;
import libs.sse.CustomSseEmitterList;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("textarea")
public class TextareaController{

    private final CustomSseEmitterList sseEmitters;

    public TextareaController() {
        this.sseEmitters = new CustomSseEmitterList();
    }


    @GetMapping(value = "/created", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> isCreated(@RequestParam int port){

        CustomSseEmitter emitter = new CustomSseEmitter(port);
        sseEmitters.add (emitter);
        return ResponseEntity.ok(emitter);
    }
}
