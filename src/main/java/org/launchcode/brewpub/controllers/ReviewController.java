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

    @GetMapping("pub/{pubReviewId}/edit")
    public String viewEditPubReviewForm(@PathVariable Integer pubReviewId,
                                 Model model) {

        Optional<PubReview> resultReview = pubReviewRepository.findById(pubReviewId);

        if (resultReview.isEmpty()) {
            return "redirect:/pubs";
        } else {
            PubReview pubReview = resultReview.get();
            Pub pub = pubReview.getPub();

            model.addAttribute("title", "Edit Review For : " + pub.getName());
            model.addAttribute("pub", pub);
            model.addAttribute("pubReview", pubReview);
        }

        return "reviews/editPubReview";
    }


    @PostMapping("pub/{pubReviewId}/edit")
    public String processEditPubReviewForm(@ModelAttribute @Valid PubReview editPubReview,
                                           Errors errors,
                                           Model model,
                                           @PathVariable Integer pubReviewId) {

        Optional<PubReview> resultOriginalReview = pubReviewRepository.findById(pubReviewId);

        if (resultOriginalReview.isEmpty()) {
            return "pubs/index";
        }

        PubReview originalReview = resultOriginalReview.get();
        Pub pub = originalReview.getPub();

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Review For : " + pub.getName());
            model.addAttribute("pubReview", editPubReview);
            model.addAttribute("errors", errors);
            model.addAttribute("pub", pub);
        } else {

            originalReview.setReviewTitle(editPubReview.getReviewTitle());
            originalReview.setReviewText(editPubReview.getReviewText());
            originalReview.setRating(editPubReview.getRating());

            pubReviewRepository.save(originalReview);
        }

        return "redirect:/pubs/view/" + pub.getId();

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

    @GetMapping("brew/{brewReviewId}/edit")
    public String viewEditBrewReviewForm(@PathVariable Integer brewReviewId,
                                         Model model) {
        Optional<BrewReview> resultReview = brewReviewRepository.findById(brewReviewId);

        if (resultReview.isEmpty()) {
            return "redirect:/pubs";
        } else {
            BrewReview brewReview = resultReview.get();
            Brew brew = brewReview.getBrew();
            Pub pub = brew.getPub();

            model.addAttribute("title", "Edit Review For : " + brew.getName());
            model.addAttribute("brew", brew);
            model.addAttribute("pub", pub);
            model.addAttribute("brewReview", brewReview);

            return "reviews/editBrewReview";
        }
    }

    @PostMapping("brew/{brewReviewId}/edit")
    public String processEditBrewReviewForm(@ModelAttribute @Valid BrewReview editBrewReview,
                                            Errors errors,
                                            Model model,
                                            @PathVariable Integer brewReviewId) {

        Optional<BrewReview> resultOriginalReview = brewReviewRepository.findById(brewReviewId);

        if (resultOriginalReview.isEmpty()) {
            return "pubs/index";
        }

        BrewReview originalReview = resultOriginalReview.get();
        Brew brew = originalReview.getBrew();
        Pub pub = brew.getPub();

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Review For : " + brew.getName());
            model.addAttribute("brewReview", editBrewReview);
            model.addAttribute("errors", errors);
            model.addAttribute("pub", pub);
            model.addAttribute("brew", brew);

            return "review/editBrewReview";
        } else {

            originalReview.setReviewTitle(editBrewReview.getReviewTitle());
            originalReview.setReviewText(editBrewReview.getReviewText());
            originalReview.setRating(editBrewReview.getRating());

            brewReviewRepository.save(originalReview);
        }

        return "redirect:/pubs/brews/view/" + brew.getId();
    }

}
