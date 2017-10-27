package com.publisher.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    @RequestMapping("/")
    public String index() {
        return "IGNORE FOR NOW";
    }

}
