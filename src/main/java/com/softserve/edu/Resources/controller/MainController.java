package com.softserve.edu.Resources.controller;

import com.softserve.edu.Resources.service.PrivilegeService;
import com.softserve.edu.Resources.service.UserService;
import com.softserve.edu.Resources.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@Transactional
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PrivilegeService privilegeService;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model, HttpServletRequest request) {
        System.out.println("Checking if ADMIN");
        if (request.isUserInRole("ROLE_ADMIN")) {
            System.out.println("Yes I am ADMIN");
        }
        System.out.println("Your IP is " + request.getRemoteAddr());
        model.addAttribute("title", "Resources");
        model.addAttribute("message", "Welcome to Resources");
        return "welcome";
    }

    @RequestMapping(value = {"/about"}, method = RequestMethod.GET)
    public String aboutPage(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }

    @RequestMapping(value = "/lookup", method = RequestMethod.GET)
    public String lookupPage(Model model) {
        
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        model.addAttribute("currentUser", username);
        model.addAttribute("title", "Look up resources");
        
        
        return "lookup";
    }
    
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public String resourcesPage(Model model, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_RESOURCE_ADMIN")) {
            return "redirect:/resources/viewTypes";
        }
        if (request.isUserInRole("ROLE_REGISTRATOR")) {
            return "redirect:/resources/registration";
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "login";
    }

/*    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users() {
        ModelAndView usersModel = new ModelAndView("administration/users");
        usersModel.addObject("users", userService.getAllUsers());
        return usersModel;
    }*/



/*    //rn - roleName
    //TODO its not safe to show our role names in the URL so we need to ...
    @RequestMapping(value = "/roleInfo", params = {"rn"}, method = RequestMethod.GET)
    public ModelAndView roleInfo(@RequestParam Map<String, String> queryUser) {
        String roleName = queryUser.get("rn");
        ModelAndView model = new ModelAndView("administration/roleInfo");
        model.addObject("list", roleService.getRolePrivileges(roleName));
        model.addObject("roleName", roleName);
        return model;
    }*/

    /*@RequestMapping(value = "/userEdit", params = {"uid"}, method = RequestMethod.GET)
    public ModelAndView userEdit(@RequestParam Map<String, String> queryUser) {
        Long userId = Long.parseLong(queryUser.get("uid"));
        System.out.println("useerID is " + userId);
        ModelAndView model = new ModelAndView("administration/userEdit");
        model.addObject("user", userService.getUserById(userId));
        model.addObject("uid", userId);
        return model;
    }*/

    @RequestMapping(value = "/addRole", method = RequestMethod.GET)
    public ModelAndView addRole(){
        ModelAndView model = new ModelAndView("administration/roleAdd");
        model.addObject("list", privilegeService.getAllPrivileges());
        return model;
    }


    //Returns list of privileges
    @RequestMapping(value = {"/privileges"}, method = RequestMethod.GET)
    public ModelAndView privilegesPage() {
        ModelAndView model = new ModelAndView("administration/privileges");

        model.addObject("list", privilegeService.getAllPrivileges());
        return model;
    }


    //SuccessfulUserCreation
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessful";
    }



    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            model.addAttribute("message", "Hi " + principal.getName()
                    + "<br> You do not have permission to access this page!");
        } else {
            model.addAttribute("msg",
                    "You do not have permission to access this page!");
        }
        return "403";
    }



    @RequestMapping(value = "/privilegetest", method = RequestMethod.GET)
    public String privilegetest() {
        return "administration/privileges2";
    }
}