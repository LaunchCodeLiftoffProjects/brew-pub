package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.AbstractEntity;
import org.launchcode.brewpub.models.Brew;
import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    PubRepository pubRepository;

    @Autowired
    BrewRepository brewRepository;

    @RequestMapping("")
    public String index(Model model) {
        List<Pub> allPubs = pubRepository.findAll();
        List<Brew> allBrews = brewRepository.findAll();

        model.addAttribute("title","Home");

        model.addAttribute("recentPubs", getFiveMostRecent(allPubs));
        model.addAttribute("recentBrews", getFiveMostRecent(allBrews));

        model.addAttribute("popularPubs", getFiveMostPopular(allPubs));
        model.addAttribute("popularBrews", getFiveMostPopular(allBrews));
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

    private List<? extends AbstractEntity> getFiveMostPopular(List<? extends AbstractEntity> items) {
        if (items == null || items.isEmpty()) {
            List<AbstractEntity> empty = new ArrayList<>();
            return empty;
        } else {
            List<AbstractEntity> fiveMostPopular = new ArrayList<>();
            int length = (items.size() >= 5) ? 5 : items.size();

            if (items.get(0).getClass() == Brew.class) {
                List<Brew> temp = new ArrayList<>();

                for (AbstractEntity item : items) {
                    temp.add((Brew) item);
                }

                Collections.sort(temp, (o1, o2) -> {
                    Integer sizeBrewOne = o1.getBrewFavoriteUser().size();
                    Integer sizeBrewTwo = o2.getBrewFavoriteUser().size();

                    return sizeBrewTwo - sizeBrewOne;
                });

                for (int i = 0; i < length; i++) {
                    fiveMostPopular.add(temp.get(i));
                }

            } else if (items.get(0).getClass() == Pub.class) {
                List<Pub> temp = new ArrayList<>();

                for (AbstractEntity item : items) {
                    temp.add((Pub) item);
                }

                Collections.sort(temp, (o1, o2) -> {
                    Integer sizeBrewOne = o1.getPubFavoriteUser().size();
                    Integer sizeBrewTwo = o2.getPubFavoriteUser().size();

                    return sizeBrewTwo - sizeBrewOne;
                });

                for (int i = 0; i < length; i++) {
                    fiveMostPopular.add(temp.get(i));
                }

                return fiveMostPopular;
            }

            return fiveMostPopular;
        }
    }

}
