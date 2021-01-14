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

        Optional<Pub> result = pubRepository.findById(pubId);

        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        } else {
            Pub pub = result.get();
            model.addAttribute("brewpub", pub);
            model.addAttribute("title", "Add Review For : " + pub.getName());
            model.addAttribute("pubReview", new PubReview());
            return "reviews/reviewPub";
        }
    }

    @PostMapping("pub")
    public String processPubReviewForm(@ModelAttribute @Valid PubReview newPubReview,
                                       Errors errors,
                                       Model model,
                                       @RequestParam Integer pubId) {

        Optional<Pub> result = pubRepository.findById(pubId);
        Pub pub = result.get();

        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        }

        if (errors.hasErrors()) {

            model.addAttribute("title", "Add Review For : " + pub.getName());
            model.addAttribute("pubReview", newPubReview);
            model.addAttribute("errors", errors);
            model.addAttribute("brewpub", result.get());

            return "reviews/reviewPub";
        } else {
            newPubReview.setPub(pub);

            pubReviewRepository.save(newPubReview);
        }

        return "redirect:/pubs/view/" + pubId;
    }

    // Brew Review

    @GetMapping("brew/{brewId}")
    public String viewBrewReviewForm(@PathVariable Integer brewId,
                                     Model model) {

        Optional<Brew> resultBrew = brewRepository.findById(brewId);

        if (resultBrew.isEmpty()) {
            return "redirect:/pubs";
        } else {
            Brew brew = resultBrew.get();
            Pub pub = brew.getPub();

            model.addAttribute("title", "Add Review For : " + brew.getName());
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
                                        @RequestParam Integer brewId) {
        Optional<Brew> resultBrew = brewRepository.findById(brewId);

        if (resultBrew.isEmpty()) {
            return "pubs/index";
        }

        Brew brew = resultBrew.get();
        Pub pub = brew.getPub();

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Review For : " + brew.getName());
            model.addAttribute("brewReview", newBrewReview);
            model.addAttribute("errors", errors);
            model.addAttribute("pub", pub);
            model.addAttribute("brew", brew);

            return "reviews/reviewBrew";
        } else {
            newBrewReview.setBrew(brew);
            brewReviewRepository.save(newBrewReview);
        }

        return "redirect:/pubs/brews/view/" + brewId;

    }

}
