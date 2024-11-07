package org.launchcode.techjobsmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController extends TechJobsController{

    @GetMapping(value = "/")
    public String index() {

        // #SUPER BONUS MISSION - below code gone to TechJobsController
        // HashMap<String, String> actionChoices = new HashMap<>();
        // actionChoices.put("search", "Search");
        // actionChoices.put("list", "List");

        // Model model not longer needed as parameters to current function
        // model.addAttribute("actions", actionChoices);

        return "index";
    }

}

