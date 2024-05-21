package org.fullstack4.teenflea.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/main")
public class MainController {
    @GetMapping("/main")
    public void mainGET(Model model){
        model.addAttribute("menu", "메인");
    }
}
