package org.launchcode.brewpub.controllers;


import org.launchcode.brewpub.models.Brew;
import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.BrewReviewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("pubs/brews")
public class BrewController {

    @Autowired
    private PubRepository pubRepository;

    @Autowired
    private BrewReviewRepository brewReviewRepository;

    @Autowired
    private BrewRepository brewRepository;

    @GetMapping("{pubId}/add")
    public String displayAddBrewForm(@PathVariable Integer pubId,
                                     Model model) {
        if (pubId == null) {
            return "pubs/index";
        } else {
            Optional<Pub> result = pubRepository.findById(pubId);
            if (result.isEmpty()) {
                return "pubs/index";
            } else {
                Pub pub = result.get();
                model.addAttribute("brewpub", pub);
                model.addAttribute(new Brew());
                return "brews/add";
            }
        }
    }

    @RequestMapping("add")
    public String processAddBrewForm(@RequestParam Integer pubId,
                                     Model model,
                                     @ModelAttribute @Valid Brew newBrew,
                                     Errors errors) {
        if (pubId == null) {
            return "pubs/index";
        } else if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return "brews/add";
        } else {
            Optional<Pub> result = pubRepository.findById(pubId);
            Pub pub = result.get();
            newBrew.setPub(pub);
            brewRepository.save(newBrew);
            return "redirect:/pubs/view/" + pubId;
        }
    }
}
