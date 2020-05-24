package com.webencyclop.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("page", "../templates/index");
        model.addAttribute("title", "Loginowanie");

        return "../shared/_Layout" ;
    }

    @RequestMapping(value = "/home/kontakt", method = RequestMethod.GET)
    public String kontakt(Model model) {

        model.addAttribute("page", "../templates/kontakt");
        model.addAttribute("title", "Kontakt");

        return "../shared/_Layout" ;
    }

    @RequestMapping(value = "/home/oferta", method = RequestMethod.GET)
    public String oferta(Model model) {

        model.addAttribute("page", "../templates/oferta");
        model.addAttribute("title", "Oferta");

        return "../shared/_Layout" ;
    }

    @RequestMapping(value = "/home/privacy", method = RequestMethod.GET)
    public String privacy(Model model) {

        model.addAttribute("page", "../templates/privacy");
        model.addAttribute("title", "Privacy");

        return "../shared/_Layout" ;
    }

}