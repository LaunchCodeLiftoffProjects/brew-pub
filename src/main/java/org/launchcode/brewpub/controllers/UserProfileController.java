package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.*;
import org.launchcode.brewpub.models.dto.CreateAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping
public class UserProfileController {

    @Autowired
    private BrewReviewRepository brewReviewRepository;

    @Autowired
    private PubReviewRepository pubReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("userProfile/{userName}")
    public String viewUserProfile(@PathVariable String userName,
                              Model model){
        Optional<User> resultUser = Optional.ofNullable(userRepository.findByUsername(userName));
        if (resultUser.isEmpty()){
            return "redirect:";
        } else {
            User user = resultUser.get();

            model.addAttribute("user",user);
            model.addAttribute("title", "tempUserProfile");
            model.addAttribute("pubReviews", pubReviewRepository.findAllByUserId(user.getId()));
            model.addAttribute("brewReviews", brewReviewRepository.findAllByUserId(user.getId()));
            return "/tempUserProfile";
        }
    }
}
