package com.softserve.edu.Resources.service.impl;

import com.softserve.edu.Resources.dao.RoleDAO;
import com.softserve.edu.Resources.dao.UserDetailsDAO;
import com.softserve.edu.Resources.entity.UserDetails;
import com.softserve.edu.Resources.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserDetailsDAO userDetailsDAO;
    @Autowired
    RoleDAO roleDAO;


    @Transactional
    public Optional<UserDetails> getUserDetailsByEmail(String email){
        Optional<UserDetails> userDetails = userDetailsDAO.findByEmail(email);
        System.out.println("Privileges extracted (commented)");

        return userDetails;
    }

    @Transactional
    public Optional<UserDetails> getUserDetailsByUserId(Long id){
        Optional<UserDetails> userDetails = userDetailsDAO.findByUserId(id);

        return userDetails;
    }

    public List<UserDetails> getAllUsers(){
        List<UserDetails> userDetailsList = userDetailsDAO.getAllUserDetails();
        return userDetailsList;
    }
}
