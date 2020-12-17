package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.BrewReview;
import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.Review;
import org.launchcode.brewpub.models.data.BrewReviewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.launchcode.brewpub.models.data.PubReviewRepository;
import org.launchcode.brewpub.models.PubReview;
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


    // Pub Review

    @GetMapping("pub" + "/{pubId}")
    public String viewPubReviewForm(Model model, @PathVariable Integer pubId) {
        // TODO: pass-in userId using session ID and session cookie to look up
        if (pubId.equals(null)) {
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
                                       @RequestParam int pubId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Review");
            model.addAttribute("brewpub", "name-of-a-brewpub");
            model.addAttribute("pubReview", newPubReview);
            model.addAttribute("errors", errors);

//            model.addAttribute("pubId",pubId);

            return "reviews/reviewPub";
        } else {
            Optional<Pub> result = pubRepository.findById(pubId);
            Pub pub = result.get();
            newPubReview.setPub(pub);

            pubReviewRepository.save(newPubReview);
        }

        return "redirect:/pubs/view/" + pubId;
    }

    // Brew Review

    @GetMapping("brew"
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

    @PostMapping("brew")
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
//        return "redirect:/pubs/view/" + pubId;
        return "redirect:/";
    }

}
