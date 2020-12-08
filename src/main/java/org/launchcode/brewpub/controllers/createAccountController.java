package org.launchcode.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Controller
public class createAccountController {

    @GetMapping("/createAccount")
    public String viewCreateAccountForm(){
        return "accountCreation";
    }

}
