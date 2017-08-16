package com.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learn.service.MainService;

@RestController
@RequestMapping("/learn")
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Void> testReactiveZip() {

        mainService.testReactiveZip();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}