package com.softserve.edu.Resources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.edu.Resources.dto.Message;
import com.softserve.edu.Resources.dto.RequestDTO;
import com.softserve.edu.Resources.entity.ResourceRequest;
import com.softserve.edu.Resources.service.impl.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Controller
@Transactional
@RequestMapping("/resources")
public class ResourceRequestAdmController {

    @Autowired
    RequestService requestService;

    @RequestMapping(value = {"/assignRequest"}, method = RequestMethod.POST)
    public @ResponseBody
    String assign(@RequestParam("id") String id
    ) {
        System.out.println(Long.parseLong(id));
        org.springframework.security.core.userdetails.User userSpring =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResourceRequest request = requestService.assignResourceAdmin(Long.parseLong(id), userSpring.getUsername());
        String json="success";
        RequestDTO requestDTO=new RequestDTO(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0"));
            json = objectMapper.writeValueAsString(requestDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }


    @RequestMapping(value = {"/sendResponce"}, method = RequestMethod.POST)
    public @ResponseBody
    String sendResponse(@RequestBody String json) {
        Message message;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            message = objectMapper.readValue(json, Message.class);
            System.out.println(message);
            requestService.response(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


    @RequestMapping(value = {"/requests"}, method = RequestMethod.GET)
    public String requests(Model model) {
        org.springframework.security.core.userdetails.User userSpring =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userSpring.getUsername());
        model.addAttribute("resourceAdmin", userSpring.getUsername());
        model.addAttribute("resourceRequest", requestService.getNewResourcesRequest());
        return "resourceRequest";
    }

    @RequestMapping(value = {"/history"}, method = RequestMethod.GET)
    public String requestsHistory(Model model) {
        model.addAttribute("resourceRequest", requestService.getHistoryResourcesRequest());
        return "resourceRequestHistory";
    }


    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String requestsDetails(@PathVariable int id, Model model) {

        ResourceRequest request = requestService.getRequestById(id);
        model.addAttribute("request", request);
        return "resourceRequestDetails";
    }
}