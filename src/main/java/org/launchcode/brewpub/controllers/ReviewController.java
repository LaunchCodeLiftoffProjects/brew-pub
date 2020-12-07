package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.Review;
import org.launchcode.brewpub.models.data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/review"
//            + "/{pubId}"
    )
    public String viewReviewForm(Model model
//                                , @PathVariable int pubId
                                ) {
        // TODO: pass-in brew-pub info to model using pubId
        // TODO: pass-in userId using session ID and session cookie to look up
        model.addAttribute("title", "Review");
        model.addAttribute("brewpub", "name-of-a-brewpub");

        model.addAttribute(new Review());
        return "review";
    }

    @PostMapping("/review")
    public String processReviewForm(@ModelAttribute @Valid Review newReview,
                                    Errors errors,
                                    Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Review");
            model.addAttribute("brewpub", "name-of-a-brewpub");
            model.addAttribute("review", newReview);
            model.addAttribute("errors", errors);

//            model.addAttribute("pubId",pubId);

            return "review";
        }

        reviewRepository.save(newReview);

        // TODO: return (redirect) to brew-pub detail view
        return "redirect:";
    }

}
