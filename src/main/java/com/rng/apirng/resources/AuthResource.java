package com.rng.apirng.resources;

import com.rng.apirng.dto.EmailDTO;
import com.rng.apirng.security.JWTUtil;
import com.rng.apirng.security.UserSS;
import com.rng.apirng.services.AuthService;
import com.rng.apirng.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/refresh_token")
    public ResponseEntity<?> refreshToken(HttpServletResponse response){

        UserSS userSS = UserService.authenticated();
        String token = jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/forgot")
    public ResponseEntity<?> forgot(@Valid @RequestBody EmailDTO emailDTO){
        authService.sendNewPassword(emailDTO.getEmail());

        return ResponseEntity.noContent().build();
    }
}