package com.food.foodorderapi.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.service.JobService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
public class CronController {

    private final JobService jobService;

    @DeleteMapping("/clean")
    public ResponseMessageBuilder.ResponseMessage<Void> clean() {
        jobService.clean();
        return new ResponseMessageBuilder<Void>().success().build();
    }


}
