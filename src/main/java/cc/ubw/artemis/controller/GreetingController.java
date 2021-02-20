/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.ubw.artemis.controller;

import cc.ubw.artemis.vo.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Author Allen
 * Date 2019年11月11日 下午7:53:06
 * Version V1.0
 * Desc
 */
@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        Greeting g = new Greeting();
        g.setContent("testtttt");
        model.addAttribute("g", g);
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "result";
    }

}

