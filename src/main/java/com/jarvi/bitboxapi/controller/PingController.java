package com.jarvi.bitboxapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PingController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String pingPong() {
        return "pong";
    }

}
