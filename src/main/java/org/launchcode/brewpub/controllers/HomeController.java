package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.AbstractEntity;
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
        model.addAttribute("recentPubs", getFiveMostRecent(pubRepository.findAll()));
        model.addAttribute("recentBrews", getFiveMostRecent(brewRepository.findAll()));
        return "index";
    }

    private List<? extends AbstractEntity> getFiveMostRecent(List<? extends AbstractEntity> items) {
        if (items == null || items.isEmpty()) {
            List<AbstractEntity> empty = new ArrayList<>();
            return empty;
        } else if (items.size() <= 5) {
            return items;
        } else {
            List<AbstractEntity> fiveMostRecent = new ArrayList<>();
            int index = items.size() - 5;
            for (int i = index; i < items.size(); i++ ) {
                fiveMostRecent.add(items.get(i));
            }
            return fiveMostRecent;
        }
    }

}
