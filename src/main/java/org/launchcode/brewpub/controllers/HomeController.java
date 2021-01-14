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

        model.addAttribute("recentPubs", getMostRecent(allPubs, 5));
        model.addAttribute("recentBrews", getMostRecent(allBrews, 5));

        model.addAttribute("popularPubs", getMostPopular(allPubs, 5));
        model.addAttribute("popularBrews", getMostPopular(allBrews, 5));
        return "index";
    }

    private List<? extends AbstractEntity> getMostRecent(List<? extends AbstractEntity> items, Integer length) {
        List<AbstractEntity> mostRecent = new ArrayList<>();

        if (items != null && !items.isEmpty()) {

            length = (items.size() >= length) ? length : items.size();

            int index = items.size() - length;
            for (int i = index; i < items.size(); i++) {
                mostRecent.add(items.get(i));
            }
        }
        return mostRecent;
    }

    private List<? extends AbstractEntity> getMostPopular(List<? extends AbstractEntity> items, Integer length) {
        List<AbstractEntity> mostPopular = new ArrayList<>();

        if (items != null && !items.isEmpty()) {

            length = (items.size() >= length) ? length : items.size();

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
                    mostPopular.add(temp.get(i));
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
                    mostPopular.add(temp.get(i));
                }

                return mostPopular;
            }

        }
        return mostPopular;
    }

}
