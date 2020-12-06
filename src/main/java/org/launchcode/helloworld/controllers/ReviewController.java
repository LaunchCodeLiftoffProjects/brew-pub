package org.launchcode.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    @GetMapping("/review")
    public String viewReviewForm(Model model) {
        // TODO: pass-in brew-pub info to model
        model.addAttribute("title", "Review");
        model.addAttribute("brewpub", "name-of-a-brewpub");
        return "review";
    }

    @PostMapping("/review")
    public String processReviewForm(Model model) {
        // TODO: validate form data
        // TODO: add form data to database
        // TODO: return (redirect) to brew-pub detail view
        return "redirect:";
    }

}
