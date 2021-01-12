package org.launchcode.brewpub.controllers;


import org.launchcode.brewpub.models.*;
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
import java.util.List;
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

    @GetMapping("/view/{brewId}")
    public String viewBrew(
                           @PathVariable Integer brewId,
                           Model model,
                           Principal principal) {

        Optional<Brew> resultBrew = brewRepository.findById(brewId);
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));

        if (resultBrew.isEmpty()) {
            return "redirect:/pubs";
        } else {
            Brew brew = resultBrew.get();
            Pub pub = brew.getPub();

            List<BrewReview> reviews = brewReviewRepository.findAllByBrewId(brewId);

            if (resultUser.isPresent()) {
                User user = resultUser.get();
                Boolean isFavorite = user.getFavoriteBrews().contains(brew);
                model.addAttribute("isFavorite", isFavorite);
            } else {
                model.addAttribute("isFavorite", false);
            }

            model.addAttribute("brew", brew);
            model.addAttribute("pub", pub);
            model.addAttribute("title","View Brew : " + brew.getName());
            model.addAttribute("reviews", reviews);
            model.addAttribute("favoritesCount", brew.getBrewFavoriteUser().size());
            model.addAttribute("averageRating", calculateAverageRating(reviews));

        }
        return "brews/view";
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

            return "redirect:/pubs/brews/view/" + brew.getId();
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

            return "redirect:/pubs/brews/view/" + brew.getId();
        }
        return "redirect:";
    }

    private Double calculateAverageRating(List<BrewReview> reviews) {
        Integer ratingTotal = 0;
        Integer numberOfRatings = reviews.size();

        for (Review review : reviews) {
            ratingTotal += review.getRating();
        }

        Double average = ratingTotal.doubleValue() / numberOfRatings.doubleValue();
        Long result = Math.round(average*10);

        return result.doubleValue()/10;
    }
}
