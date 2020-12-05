package org.launchcode.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RateController {

    @GetMapping("/rate")
    public String viewRateForm(Model model) {
        // TODO: pass-in brew-pub info to model
        model.addAttribute("title", "Rate");
        model.addAttribute("brewpub", "name-of-a-brewpub");
        return "rate";
    }

    @PostMapping("/rate")
    public String processRateForm(Model model) {
        // TODO: validate form data
        // TODO: add form data to database
        // TODO: return (redirect) to brew-pub detail view
        return "redirect:";
    }

}
