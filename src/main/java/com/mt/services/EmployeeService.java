package com.mt.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/employee")
public class EmployeeService {

    @RequestMapping(value = "/getEmployeeDetails", method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeeDetails(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
            throws JSONException {

        JSONObject js = new JSONObject();
        js.put("Name", "Ankit Sharma");
        js.put("Calling Name", "Ankit");

        // Creating a JSON array for multiple values
        JSONArray likesArray = new JSONArray();
        likesArray.put("Share");
        likesArray.put("Subscribe");
        js.put("Like", likesArray);

        // Adding a proper key-value pair
        js.put("Subscribe", "Do Subscribe to my YouTube channel");

        return js.toString();
    }
}
