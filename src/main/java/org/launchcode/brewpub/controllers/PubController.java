package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.data.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("pubs")
public class PubController {

    @Autowired
    private PubRepository pubRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Pubs");
        model.addAttribute("pubs", pubRepository.findAll());
        return "/pubs/index";
    }

    @GetMapping("add")
    public String displayAddPubForm(Model model) {
        model.addAttribute("title", "Pubs");
        model.addAttribute("pubs", pubRepository.findAll());
        model.addAttribute(new Pub());
        return "/pubs/add";
    }

    @PostMapping("add")
    public String processAddPubForm(@ModelAttribute @Valid Pub newPub, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("title", "Pubs");
            model.addAttribute("pubs", pubRepository.findAll());
            //model.addAttribute("pubs", newPub);
            return "/pubs/add";
        }

        pubRepository.save(newPub);
        return "redirect:";
    }

    @GetMapping("view/{pubID}")
    public String viewPubInfo(@PathVariable Integer pubID,
                              Model model) {
        if (pubID == null) {
            return "pubs/index";
        } else {
            Optional<Pub> result = pubRepository.findById(pubID);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Pub ID");
            } else
                model.addAttribute("pub", pubRepository.findById(pubID));
            Pub pub = result.get();
            model.addAttribute("title", "Pub: " + pub.getName());
            model.addAttribute("pub", pub);
        }
        return "pubs/view";
    }


}
