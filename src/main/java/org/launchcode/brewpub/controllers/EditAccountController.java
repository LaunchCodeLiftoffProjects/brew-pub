package org.launchcode.brewpub.controllers;


import org.launchcode.brewpub.models.*;
import org.launchcode.brewpub.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EditAccountController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrewReviewRepository brewReviewRepository;

    @Autowired
    PubReviewRepository pubReviewRepository;

    @Autowired
    BrewRepository brewRepository;

    @Autowired
    PubRepository pubRepository;

    @GetMapping("/accountDetails")
    public String showUserAccountInfo(Model model, Principal principal) {
        model.addAttribute("title", "accountDetails");
        Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        if (resultUser.isEmpty()) {
            return "index";
        } else {
            User user = resultUser.get();
            model.addAttribute("user", user);
        }
        return "editAccount/accountDetails";
    }

    @GetMapping("/editAccount")
    public String showEditAccountForm(Model model, Principal principal) {
        model.addAttribute("title", "editAccount");
        Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        if (resultUser.isEmpty()) {
            return "index";
        } else {
            User user = resultUser.get();
            model.addAttribute("user", user);
        }
        return "editAccount/editAccount";
    }

    @RequestMapping("/resetAccountInformation")
    public String processEditAccountForm(@ModelAttribute @Valid User userTemp,
                                         Errors errors, Model model, Principal principal, HttpServletRequest request, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("user", userTemp);
            model.addAttribute("title", "editAccount");
            model.addAttribute("errors", errors);
            return "editAccount/editAccount";
        } else {
            Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));

            if (resultUser.isEmpty()) {
                return "index";
            }

            User user = resultUser.get();

            user.setEmail(userTemp.getEmail());
            user.setFirstName(userTemp.getFirstName());
            user.setLastName(userTemp.getLastName());

            if (!user.getUsername().equals(userTemp.getUsername())) {
                user.setUsername(userTemp.getUsername());
                userRepository.save(user);
                model.addAttribute("message", "Account information successfully changed.");
                return "login";
            } else {
                userRepository.save(user);
                return "redirect:/accountDetails";
            }
        }
    }

    @GetMapping("deleteAccount")
    public String showDeleteAccountConfirmation(Model model) {
        model.addAttribute("title", "Delete Account");
        return "editAccount/deleteAccount";
    }

    @PostMapping("deleteAccount")
    public String processDeleteAccount(Principal principal, HttpServletRequest request) {
        request.getSession().invalidate();

        Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        if (resultUser.isPresent()) {
            User user = resultUser.get();

            List<Brew> favoriteBrews = new ArrayList<>(user.getFavoriteBrews());
            if (!favoriteBrews.isEmpty()) {
                for (Brew brew : favoriteBrews) {
                    user.removeFavoriteBrew(brew);
                    brew.removeBrewFavoriteUser(user);
                    userRepository.save(user);
                    brewRepository.save(brew);
                }
            }

            List<Pub> favoritePubs = new ArrayList<>(user.getFavoritePubs());
            if (!favoritePubs.isEmpty()) {
                for (Pub pub : favoritePubs) {
                    user.removeFavoritePub(pub);
                    pub.removePubFavoriteUser(user);
                    userRepository.save(user);
                    pubRepository.save(pub);
                }
            }

            List<BrewReview> brewReviews = brewReviewRepository.findAllByUserId(user.getId());
            List<PubReview> pubReviews = pubReviewRepository.findAllByUserId(user.getId());

            brewReviewRepository.deleteAll(brewReviews);
            pubReviewRepository.deleteAll(pubReviews);

            userRepository.delete(user);
        }

        return "redirect:";
    }
}
