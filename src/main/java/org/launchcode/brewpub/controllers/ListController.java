package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.BrewReviewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.launchcode.brewpub.models.data.PubReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping( value = "list")
public class ListController {
    @Autowired
    private PubRepository pubRepository;

    @Autowired
    private PubReviewRepository pubReviewRepository;

    @Autowired
    private BrewReviewRepository brewReviewRepository;

    @Autowired
    private BrewRepository brewRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, String> puborbrewChoices = new HashMap<>();

    public ListController() {
        columnChoices.put("all", "All");
        columnChoices.put("name", "Name");
        columnChoices.put("address", "Address");
        columnChoices.put("city", "City");
        columnChoices.put("state", "State");
        columnChoices.put("abv", "ABV");
        columnChoices.put("style", "Style");
        columnChoices.put("description", "Description");
        puborbrewChoices.put("pub", "Pub");
        puborbrewChoices.put("brew", "Brew");
        puborbrewChoices.put("both", "Both");
    }

    @RequestMapping("")
    public String list(Model model) {
        model.addAttribute("pubs", pubRepository.findAll());
        model.addAttribute("brews", brewRepository.findAll());

        return "list";
    }

}
