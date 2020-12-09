package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.BrewReview;
import org.launchcode.brewpub.models.Review;
import org.launchcode.brewpub.models.data.BrewReviewRepository;
import org.launchcode.brewpub.models.data.PubReviewRepository;
import org.launchcode.brewpub.models.PubReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    private BrewReviewRepository brewReviewRepository;

    @Autowired
    private PubReviewRepository pubReviewRepository;


    // Pub Review

    @GetMapping("/review/pub"
//            + "/{pubId}"
    )
    public String viewPubReviewForm(Model model
//                                , @PathVariable int pubId
                                ) {
        // TODO: pass-in brew-pub info to model using pubId
        // TODO: pass-in userId using session ID and session cookie to look up
        model.addAttribute("title", "Review");
        model.addAttribute("brewpub", "name-of-a-brewpub");

        model.addAttribute("pubReview", new PubReview());
        return "reviews/reviewPub";
    }

    @PostMapping("/review/pub")
    public String processPubReviewForm(@ModelAttribute @Valid PubReview newPubReview,
                                    Errors errors,
                                    Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Review");
            model.addAttribute("brewpub", "name-of-a-brewpub");
            model.addAttribute("pubReview", newPubReview);
            model.addAttribute("errors", errors);

//            model.addAttribute("pubId",pubId);

            return "reviews/reviewPub";
        }

        pubReviewRepository.save(newPubReview);

        // TODO: return (redirect) to brew-pub detail view
        return "redirect:/";
    }

    // Brew Review

    @GetMapping("/review/brew"
//            + "/{brewId}"
    )
    public String viewBrewReviewForm(Model model
//                                , @PathVariable int brewId
    ) {
        // TODO: pass-in brew-pub info to model using pubId
        // TODO: pass-in userId using session ID and session cookie to look up
        model.addAttribute("title", "Review");
        model.addAttribute("brewpub", "name-of-a-brewpub");

        model.addAttribute("brewReview", new BrewReview());
        return "reviews/reviewBrew";
    }

    @PostMapping("/review/brew")
    public String processBrewReviewForm(@ModelAttribute @Valid BrewReview newBrewReview,
                                    Errors errors,
                                    Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Review");
            model.addAttribute("brewpub", "name-of-a-brewpub");
            model.addAttribute("brewReview", newBrewReview);
            model.addAttribute("errors", errors);

//            model.addAttribute("pubId",pubId);

            return "reviews/reviewBrew";
        }

        brewReviewRepository.save(newBrewReview);

        // TODO: return (redirect) to brew-pub detail view
        return "redirect:/";
    }

}
