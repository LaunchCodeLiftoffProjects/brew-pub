package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.Brew;
import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    PubRepository pubRepository;

    @Autowired
    BrewRepository brewRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title","Home");
        model.addAttribute("recentPubs", getFiveMostRecentPubs(pubRepository.findAll()));
        model.addAttribute("recentBrews", getFiveMostRecentBrews(brewRepository.findAll()));
        return "index";
    }

    private List<Pub> getFiveMostRecentPubs(List<Pub> items) {
        if (items.size() <= 5) {
            return items;
        } else {
            List<Pub> pubsRecent = new ArrayList<>();
            int index = items.size() - 5;
            for (int i = index; i < items.size(); i++ ) {
                pubsRecent.add(items.get(i));
            }
            return pubsRecent;
        }
    }

    private List<Brew> getFiveMostRecentBrews(List<Brew> items) {
        if (items.size() <= 5) {
            return items;
        } else {
            List<Brew> pubsRecent = new ArrayList<>();
            int index = items.size() - 5;
            for (int i = index; i < items.size(); i++) {
                pubsRecent.add(items.get(i));
            }
            return pubsRecent;
        }
    }
}
