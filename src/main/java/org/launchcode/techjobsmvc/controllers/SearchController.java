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



//      BONUS MISSION #1: Focus on search radio button - FAILS MANY TESTS; so not going to implement it
//      Solution1: HTTPS session passed as parameter to the function
//      Solution2: Also tried hidden input type option, both solutions failing tests
//
//        String searchFocus = "";
//        if (session.toString().equals("") || session.toString().equals("all")){
//            searchFocus= "all";       // B.M #1:
//        }
//        else if(session.toString().equals("positionType")){
//            searchFocus= "positionType";       // B.M #1:
//        }
//        else if(session.toString().equals("employer")){
//            searchFocus= "employer";       // B.M #1:
//        }
//        else if(session.toString().equals("coreCompetency")){
//            searchFocus= "coreCompetency";       // B.M #1:
//        }
//        else if(session.toString().equals("location")){
//            searchFocus= "location";       // B.M #1:
//        }
//
//        session.setAttribute("searchType", searchFocus);       // B.M #1:


        return "search";
    }

}

