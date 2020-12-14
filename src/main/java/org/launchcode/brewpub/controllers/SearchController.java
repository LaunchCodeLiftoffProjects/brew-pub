package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.PubData;
import org.launchcode.brewpub.models.data.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.launchcode.brewpub.controllers.ListController.columnChoices;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private PubRepository pubRepository;

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Pub> pubs;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            pubs = pubRepository.findAll();
        } else {
            pubs = PubData.findByColumnAndValue(searchType, searchTerm, pubRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Pubs with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("pubs", pubs);

        return "search";
    }


}
