package org.launchcode.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class createAccountController {

    @GetMapping("/createAccount")
    public String viewCreateAccountForm(){
        return "accountCreation";
    }

}
