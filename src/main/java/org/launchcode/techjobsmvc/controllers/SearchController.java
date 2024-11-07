package org.launchcode.techjobsmvc.controllers;

import jakarta.servlet.http.HttpSession;
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

    String radioStatus = "";                            // BONUS MISSION 1: maintaining state of radio button based on user selection

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);

        radioStatus = "all";                           // B.M. 1: radio button state
        model.addAttribute("radioStatus", radioStatus);

        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    // path: /search/results
    @PostMapping("results")
    public String displaySearchResults(Model m,
                                       @RequestParam String searchType,
                                       @RequestParam(required=false) String searchTerm){
        ArrayList<Job> jobs;
        radioStatus= searchType;

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

        m.addAttribute("columns", columnChoices);
        m.addAttribute("jobs", jobs);
        m.addAttribute("radioStatus", radioStatus);     // B.M. 1: To display radio buttons for searchType

        return "search";
    }

}

