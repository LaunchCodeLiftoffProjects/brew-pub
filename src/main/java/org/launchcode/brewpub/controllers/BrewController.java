package org.launchcode.brewpub.controllers;


import org.launchcode.brewpub.models.Brew;
import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.BrewReviewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.launchcode.brewpub.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("{pubId}/add")
    public String displayAddBrewForm(@PathVariable Integer pubId,
                                     Model model) {

        Optional<Pub> result = pubRepository.findById(pubId);

        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        } else {
            Pub pub = result.get();
            model.addAttribute("title", "Add Brew For : " + pub.getName());
            model.addAttribute("pub", pub);
            model.addAttribute(new Brew());
            return "brews/add";

        }
    }

    @RequestMapping("add")
    public String processAddBrewForm(@ModelAttribute @Valid Brew newBrew,
                                     Errors errors,
                                     @RequestParam Integer pubId,
                                     Model model) {

        Optional<Pub> result = pubRepository.findById(pubId);
        Pub pub = result.get();

        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        } else if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            model.addAttribute("title", "Add Brew For : " + pub.getName());
            model.addAttribute("pub", pub);
            return "brews/add";
        } else {
            newBrew.setPub(pub);
            brewRepository.save(newBrew);
            return "redirect:/pubs/view/" + pubId;
        }
    }

    @GetMapping("{pubId}/view/{brewId}")
    public String viewBrew(@PathVariable Integer pubId,
                           @PathVariable Integer brewId,
                           Model model,
                           Principal principal) {

        Optional<Pub> resultPub = pubRepository.findById(pubId);
        Optional<Brew> resultBrew = brewRepository.findById(brewId);
        User user = userRepository.findByUsername(principal.getName());

        if (resultPub.isEmpty() || resultBrew.isEmpty()) {
            return "redirect:/pubs";
        } else {
            Pub pub = resultPub.get();
            Brew brew = resultBrew.get();

            if (resultPub.isEmpty() || resultBrew.isEmpty()) {
                return "pubs/index";
            } else {
                Boolean isFavorite = user.getFavoriteBrews().contains(brew);
                model.addAttribute("brew", brew);
                model.addAttribute("pub", pub);
                model.addAttribute("title","View Brew : " + brew.getName());
                model.addAttribute("reviews", brewReviewRepository.findAllByBrewId(brewId));
                model.addAttribute("isFavorite", isFavorite);
                return "brews/view";
            }
        }
    }

    @GetMapping("addFavoriteBrew/{brewId}")
    public String processAddFavoriteBrew(@PathVariable Integer brewId,
                                         Principal principal) {
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        Optional<Brew> resultBrew = brewRepository.findById(brewId);

        if (brewId == null || resultBrew.isEmpty()) {
            return "redirect:";
        } else if (principal.getName() == null || resultUser.isEmpty()) {
            Brew brew = resultBrew.get();
            return "redirect:/pubs/brews/" + brew.getPub().getId() + "/view/" + brew.getId();
        } else if (resultUser.isPresent() && resultBrew.isPresent()) {
            Brew brew = resultBrew.get();
            User user = resultUser.get();

            user.addFavoriteBrew(brew);
            brew.addBrewFavoriteUser(user);

            userRepository.save(user);
            brewRepository.save(brew);

            return "redirect:/pubs/brews/" + brew.getPub().getId() + "/view/" + brew.getId();
        }
        return "redirect:";
    }

    @GetMapping("removeFavoriteBrew/{brewId}")
    public String processRemoveFavoriteBrew(@PathVariable Integer brewId,
                                            Principal principal) {
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        Optional<Brew> resultBrew = brewRepository.findById(brewId);

        if (brewId == null || resultBrew.isEmpty()) {
            return "redirect:";
        } else if (principal.getName() == null || resultUser.isEmpty()) {
            Brew brew = resultBrew.get();
            return "redirect:/pubs/brews/" + brew.getPub().getId() + "/view/" + brew.getId();
        } else if (resultUser.isPresent() && resultBrew.isPresent()) {
            Brew brew = resultBrew.get();
            User user = resultUser.get();

            user.removeFavoriteBrew(brew);
            brew.removeBrewFavoriteUser(user);

            userRepository.save(user);
            brewRepository.save(brew);

            return "redirect:/pubs/brews/" + brew.getPub().getId() + "/view/" + brew.getId();
        }
        return "redirect:";
    }
}
