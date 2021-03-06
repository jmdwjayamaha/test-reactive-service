package com.learn.controller;

import java.util.List;

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

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public HttpEntity<Void> testReactiveSubscribe() {

        mainService.testReactiveSubscribe();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribe/v2", method = RequestMethod.GET)
    public HttpEntity<List<Integer>> testReactiveSubscribe2() {

        final List<Integer> valueList = mainService.testReactiveSubscribe2();

        return new ResponseEntity<>(valueList, HttpStatus.OK);
    }
}