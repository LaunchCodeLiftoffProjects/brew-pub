package org.launchcode.brewpub.controllers;


import org.launchcode.brewpub.models.Brew;
import org.launchcode.brewpub.models.Pub;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.BrewReviewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
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
import java.util.Optional;

@Controller
@RequestMapping("pubs/brews")
public class BrewController {

    @Autowired
    private PubRepository pubRepository;

    @Autowired
    private BrewReviewRepository brewReviewRepository;

    @Autowired
    private BrewRepository brewRepository;

    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

    @GetMapping("{pubId}/add")
    public String displayAddBrewForm(@PathVariable Integer pubId,
                                     Model model) {

        Optional<Pub> result = pubRepository.findById(pubId);

        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        } else {
            Pub pub = result.get();
            model.addAttribute("pub", pub);
            model.addAttribute(new Brew());
            return "brews/add";

        }
    }

    @RequestMapping("add")
    public String processAddBrewForm(@ModelAttribute @Valid Brew newBrew, @RequestParam("files") MultipartFile[] files,
                                     Errors errors,
                                     @RequestParam Integer pubId,
                                     Model model) throws IOException {

        Optional<Pub> result = pubRepository.findById(pubId);

        StringBuilder fileNames = new StringBuilder();
        for(MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
        }

        if (pubId == null || result.isEmpty()) {
            return "pubs/index";
        } else if (errors.hasErrors()) {
            Pub pub = result.get();
            model.addAttribute("errors", errors);
            model.addAttribute("pub", pub);
            return "brews/add";
        } else {
            Pub pub = result.get();
            newBrew.setPub(pub);
            brewRepository.save(newBrew);
            return "redirect:/pubs/view/" + pubId;
        }
    }

    @GetMapping("{pubId}/view/{brewId}")
    public String viewBrew(@PathVariable Integer pubId,
                           @PathVariable Integer brewId,
                           Model model) {

        Optional<Pub> resultPub = pubRepository.findById(pubId);
        Optional<Brew> resultBrew = brewRepository.findById(brewId);

        if (resultPub.isEmpty() || resultBrew.isEmpty()) {
            return "redirect:/pubs";
        } else {
            Pub pub = resultPub.get();
            Brew brew = resultBrew.get();

            if (resultPub.isEmpty() || resultBrew.isEmpty()) {
                return "pubs/index";
            } else {
                model.addAttribute("brew", brew);
                model.addAttribute("pub", pub);
                model.addAttribute("reviews", brewReviewRepository.findAllByBrewId(brewId));

                return "brews/view";
            }
        }
    }


}
