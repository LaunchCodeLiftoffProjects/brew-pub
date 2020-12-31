package org.launchcode.brewpub;

import org.launchcode.brewpub.controllers.BrewController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan({"org.launchcode.brewpub", "controller"})
public class BrewPubApplication {

	public static void main(String[] args) {
		new File(BrewController.uploadDirectory).mkdir();
		SpringApplication.run(BrewPubApplication.class, args);
	}

}
