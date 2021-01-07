package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.*;
import org.launchcode.brewpub.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private BrewReviewRepository brewReviewRepository;

    @Autowired
    private PubReviewRepository pubReviewRepository;

    @Autowired
    private PubRepository pubRepository;

    @Autowired
    private BrewRepository brewRepository;

    @Autowired
    private UserRepository userRepository;


    // Pub Review

    @GetMapping("pub/{pubId}")
    public String viewPubReviewForm(Model model, @PathVariable Integer pubId) {

        Optional<Pub> result = pubRepository.findById(pubId);

        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        } else {
            Pub pub = result.get();
            model.addAttribute("brewpub", pub);
            model.addAttribute("pubReview", new PubReview());
            return "reviews/reviewPub";
        }
    }

    @PostMapping("pub")
    public String processPubReviewForm(@ModelAttribute @Valid PubReview newPubReview,
                                       Errors errors,
                                       Model model,
                                       @RequestParam Integer pubId, Principal principal) {

        Optional<Pub> result = pubRepository.findById(pubId);
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));


        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        }

        if (errors.hasErrors()) {

            model.addAttribute("title", "Review");
            model.addAttribute("pubReview", newPubReview);
            model.addAttribute("errors", errors);
            model.addAttribute("brewpub", result.get());

            return "reviews/reviewPub";
        } else {
            Pub pub = result.get();
            User user = resultUser.get();
            newPubReview.setPub(pub);
            newPubReview.setUser(user);

            pubReviewRepository.save(newPubReview);
        }

        return "redirect:/pubs/view/" + pubId;
    }

    // Brew Review

    @GetMapping("{pubId}/{brewId}")
    public String viewBrewReviewForm(@PathVariable Integer pubId,
                                     @PathVariable Integer brewId,
                                     Model model) {

        Optional<Pub> resultPub = pubRepository.findById(pubId);
        Optional<Brew> resultBrew = brewRepository.findById(brewId);


        if (resultPub.isEmpty() || resultBrew.isEmpty()) {
            return "redirect:/pubs";
        } else {
            Pub pub = resultPub.get();
            Brew brew = resultBrew.get();

            model.addAttribute("brew", brew);
            model.addAttribute("pub", pub);
            model.addAttribute(new BrewReview());

            return "reviews/reviewBrew";
        }

    }

    @PostMapping("brew")
    public String processBrewReviewForm(@ModelAttribute @Valid BrewReview newBrewReview,
                                        Errors errors,
                                        Model model,
                                        @RequestParam Integer pubId,
                                        @RequestParam Integer brewId, Principal principal) {
        Optional<Pub> resultPub = pubRepository.findById(pubId);
        Optional<Brew> resultBrew = brewRepository.findById(brewId);
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));

        if (pubId == null || brewId == null || resultBrew.isEmpty() || resultPub.isEmpty()) {
            return "pubs/index";
        }

        if (errors.hasErrors()) {
            model.addAttribute("title", "Review Brew");
            model.addAttribute("brewReview", newBrewReview);
            model.addAttribute("errors", errors);
            model.addAttribute("pub", resultPub.get());
            model.addAttribute("brew", resultBrew.get());

            return "reviews/reviewBrew";
        } else {

            Brew brew = resultBrew.get();
            User user = resultUser.get();

            newBrewReview.setBrew(brew);
            newBrewReview.setUser(user);

            brewReviewRepository.save(newBrewReview);

        }

        return "redirect:/pubs/brews/" + pubId + "/view/" + brewId;

    }

}
