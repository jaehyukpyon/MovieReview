package com.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleController {

    @GetMapping(value = {"/exTemplate", "exTemplate_test", "/exSidebar"})
    public void exLayout1() {
        ;
    }

}
