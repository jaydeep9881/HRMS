package com.hrms.hrms.user.controller;

import com.hrms.hrms.user.DTO.UserRequest;
import com.hrms.hrms.user.DTO.UserResponse;
import com.hrms.hrms.user.controller.Users;
import com.hrms.hrms.user.service.UserSerivice;
import com.hrms.hrms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserSerivice serivice;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest user){
        return serivice.addUser(user);
    }
    @GetMapping("/users")
    private List<UserResponse> getAllUsers(){
        return serivice.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails= serivice.loadUserByUsername(user.getUsername());
            String token= jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }catch (Exception e) {
            return  new ResponseEntity<>("no usert found",HttpStatus.UNAUTHORIZED);
        }

    }
}

