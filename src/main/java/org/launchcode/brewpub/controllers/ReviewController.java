package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.*;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.BrewReviewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.launchcode.brewpub.models.data.PubReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    // Pub Review

    @GetMapping("pub/{pubId}")
    public String viewPubReviewForm(Model model, @PathVariable Integer pubId) {
        // TODO: pass-in userId using session ID and session cookie to look up
        if (pubId == null) {
            return "pubs/index";
        } else {
            Optional<Pub> result = pubRepository.findById(pubId);
            if (result.isEmpty()) {
                return "pubs/index";
            } else {
                Pub pub = result.get();
                model.addAttribute("brewpub", pub);
                model.addAttribute("pubReview", new PubReview());
            }
            return "reviews/reviewPub";
        }
    }

    @PostMapping("pub")
    public String processPubReviewForm(@ModelAttribute @Valid PubReview newPubReview,
                                       Errors errors,
                                       Model model,
                                       @RequestParam Integer pubId) {
        Optional<Pub> result = pubRepository.findById(pubId);

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
            newPubReview.setPub(pub);

            pubReviewRepository.save(newPubReview);
        }

        return "redirect:/pubs/view/" + pubId;
    }

    // Brew Review

    @GetMapping("{pubId}/{brewId}")
    public String viewBrewReviewForm(@PathVariable Integer pubId,
                                     @PathVariable Integer brewId,
                                     Model model) {

        // TODO: pass-in userId using session ID and session cookie to look up


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
                                        @RequestParam Integer brewId) {
        Optional<Pub> resultPub = pubRepository.findById(pubId);
        Optional<Brew> resultBrew = brewRepository.findById(brewId);


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

            Pub pub = resultPub.get();
            Brew brew = resultBrew.get();

            newBrewReview.setBrew(brew);
            brewReviewRepository.save(newBrewReview);
        }

        return "redirect:/pubs/brews/" + pubId + "/view/" + brewId;

    }

}
