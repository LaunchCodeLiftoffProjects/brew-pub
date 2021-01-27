package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.PubReview;
import org.launchcode.brewpub.models.Review;
import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.launchcode.brewpub.models.data.PubReviewRepository;
import org.launchcode.brewpub.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("pubs")
public class PubController {

    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads/";

    @Autowired
    private PubRepository pubRepository;

    @Autowired
    private PubReviewRepository pubReviewRepository;

    @Autowired
    private BrewRepository brewRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Pubs");
        model.addAttribute("pubs", pubRepository.findAll());
        return "/pubs/index";
    }

    @GetMapping("add")
    public String displayAddPubForm(Model model) {
        model.addAttribute("title", "Add Pub");
        model.addAttribute("pubs", pubRepository.findAll());
        model.addAttribute(new Pub());
        return "/pubs/add";
    }

    @RequestMapping("add")
    public String processAddPubForm(@ModelAttribute @Valid Pub newPub, Errors errors, MultipartFile[] files, Model model, Principal principal){

        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));

        if (errors.hasErrors()){
            model.addAttribute("title", "Add Pub");
            model.addAttribute("pubs", pubRepository.findAll());
//            model.addAttribute("pubs", newPub);
            return "/pubs/add";
        } else {

            for(MultipartFile file : files) {
                if (!file.isEmpty() && resultUser.isPresent()) {
                    User user = resultUser.get();

                    Path fileNameAndPath = Paths.get(
                            uploadDirectory,
                            "user" + user.getId() + "--" +
                                    file.getOriginalFilename().replaceAll("\\s", ""));

                    try {
                        Files.write(fileNameAndPath, file.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    newPub.setImagePath("/uploads/" + fileNameAndPath.getFileName().toString());
                } else {
                    newPub.setImagePath(null);
                }
            }

            pubRepository.save(newPub);
            return "redirect:";
        }


    }

    @GetMapping("view/{pubID}")
    public String viewPubInfo(@PathVariable Integer pubID,
                              Model model,
                              Principal principal) {
        Optional<Pub> resultPub = pubRepository.findById(pubID);

        User user = null;
        if (principal != null) {
            user = userRepository.findByUsername(principal.getName());
        }

        if (resultPub.isEmpty()) {
            return "pubs/index";
        } else {
            Pub pub = resultPub.get();

            List<PubReview> reviews = pubReviewRepository.findAllByPubId(pubID);

            if (user != null) {

                Boolean isFavorite = user.getFavoritePubs().contains(pub);
                Boolean isReviewable = true;
                List<PubReview> allByUserId = pubReviewRepository.findAllByUserId(user.getId());
                for (PubReview review : allByUserId) {
                    if (review.getUser().getId() == user.getId() && review.getPub().getId() == pub.getId()) {
                        isReviewable = false;
                    }

                }

                model.addAttribute("isReviewable", isReviewable);
                model.addAttribute("isFavorite", isFavorite);
            } else {
                model.addAttribute("isFavorite", false);
            }

            model.addAttribute("pub", pub);
            model.addAttribute("title", "Pub: " + pub.getName());
            model.addAttribute("reviews", reviews);
            model.addAttribute("brews", brewRepository.findAllByPubId(pubID));
            model.addAttribute("favoritesCount", pub.getPubFavoriteUser().size());
            model.addAttribute("averageRating", calculateAverageRating(reviews));

        }
        return "pubs/view";
    }

    @GetMapping("addFavoritePub/{pubId}/")
    public String processAddFavoritePub(@PathVariable Integer pubId,
                                        Principal principal) {
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        Optional<Pub> resultPub = pubRepository.findById(pubId);

        if (pubId == null || resultPub.isEmpty()) {
            return "redirect:";
        } else if (principal.getName() == null || resultUser.isEmpty()) {
            return "redirect:/pubs/view/" + pubId;
        } else if (resultPub.isPresent() && resultUser.isPresent()) {
            Pub pub = resultPub.get();
            User user = resultUser.get();

            user.addFavoritePub(pub);
            pub.addPubFavoriteUser(user);

            userRepository.save(user);
            pubRepository.save(pub);

            return "redirect:/pubs/view/" + pubId;
        }
        return "redirect:";
    }

    @GetMapping("removeFavoritePub/{pubId}/")
    public String processRemoveFavoritePub(@PathVariable Integer pubId,
                                           Principal principal) {
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        Optional<Pub> resultPub = pubRepository.findById(pubId);

        if (pubId == null || resultPub.isEmpty()) {
            return "redirect:";
        } else if (principal.getName() == null || resultUser.isEmpty()) {
            return "redirect:/pubs/view/" + pubId;
        } else if (resultPub.isPresent() && resultUser.isPresent()) {
            Pub pub = resultPub.get();
            User user = resultUser.get();

            user.removeFavoritePub(pub);
            pub.removePubFavoriteUser(user);

            userRepository.save(user);
            pubRepository.save(pub);

            return "redirect:/pubs/view/" + pubId;
        }
        return "redirect:";
    }

    private Double calculateAverageRating(List<PubReview> reviews) {
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
