package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();


    public ListController () {          // Constructor
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");                 // titles of columns
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");

        //tableChoices.put("all", JobData.findAll());                     // Task 2: Adding "All" to the 'View Jobs By Category' table
        tableChoices.put("all", "View All");
        tableChoices.put("employer", JobData.getAllEmployers());        // Getting data from .csv file
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());
    }


    // renders a view that displays a table of clickable links for the different job categories
    // localhost:8080/list
    @GetMapping(value = "")
    public String list(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("tableChoices", tableChoices);               // tableChoices
        model.addAttribute("employers", JobData.getAllEmployers());     // Methods from JobData
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());

        return "list";
    }


    // displays information for the jobs related to a selected value & category

    // i.e. takes to jobs page & lists either all jobs or column's selection choice based on user selection
    // LC Demo link: https://techjobs-mvc.launchcodelearning.org/list/jobs?column=all
    // On localhost: /list/jobs{column=[coreCompetency], value=[Ruby]}
    @GetMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam(required = false) String value) {
        ArrayList<Job> jobs;            // returns an ArrayList of type Job

        // adds title to the /jobs page based on selection
        if (column.equals("all")){
            jobs = JobData.findAll();                                          // returns all jobs
            model.addAttribute("title", "All Jobs");
        } else {
            jobs = JobData.findByColumnAndValue(column, value);                // returns a smaller set of jobs based on column & value
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }

        // adds jobs
        model.addAttribute("jobs", jobs);

        return "list-jobs";  // list-jobs.html
    }



    // BONUS MISSION #3:    --- Works!! ---   But not implemented since it causes tests to fail
    // Added new Controller Method below that displays results on linked-results.html page
    // Also, created new linked-results.html page

    // adds title to the list/results page based on selection
    // eg. http://localhost:8080/list/results?column=location&value=Kansas%20City
    @GetMapping(value = "results")
    public String listLinkedResultsByColumnAndValue(Model m, @RequestParam String column, @RequestParam(required = false) String value){
        ArrayList<Job> jobs;

        m.addAttribute("title", "All Jobs");
        m.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);

        jobs = JobData.findByColumnAndValue(column, value);
        m.addAttribute("jobs", jobs);

        return "linked-results";
    }

}

