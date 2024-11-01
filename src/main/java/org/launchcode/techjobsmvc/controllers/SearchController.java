package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    // path: /search/results
    @PostMapping("results")
    public String displaySearchResults(Model m, @RequestParam String searchType, @RequestParam(required=false) String searchTerm){
        ArrayList<Job> jobs;

        if(searchType.equals("all") || searchType.equals(""))
        {
            // jobs= JobData.findAll();
            jobs= JobData.findByValue(searchTerm);
            m.addAttribute("title", "Jobs With All: ");
        }
        else{
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);

            // Displaying user-friendly titles of searchType in results page.
            if(searchType.equals("positionType")){
                searchType="Position Type";
            }
            else if(searchType.equals("coreCompetency")){
                searchType="Skill";
            }
            m.addAttribute("title", "Jobs with " + searchType + ": " + searchTerm);
        }

        m.addAttribute("columns", columnChoices);   // to display radio buttons for searchType
        m.addAttribute("jobs", jobs);
        return "search";
    }

}

