package org.handnotes.auth;


import io.jsonwebtoken.ExpiredJwtException;
import org.handnotes.model.User;
import org.handnotes.model.requests.LoginRequest;
import org.handnotes.model.requests.LogoutRequest;
import org.handnotes.model.requests.RegisterRequest;
import org.handnotes.model.responses.*;
import org.handnotes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtUtils;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtService;
        this.userService = userService;
    }

    public ResponseEntity<IResponse> registerFlow(RegisterRequest request){

        try{
            final String username = request.getUsername();
            final String password = request.getPassword();

            // TODO: parse the input from the request

            User user = new User(username, password);
            userService.saveUser(user);
            return ResponseEntity.ok(new RegisterResponse("Registration succesfull."));
        }catch( Exception e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }


    public ResponseEntity<IResponse> authFlow(LoginRequest request) {

        final String username = request.getUsername();
        final String password = request.getPassword();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = new User(username, "");
            String token = jwtUtils.createToken(user);

            return ResponseEntity.ok(new LoginResponse(username, token));
        }catch(AuthenticationException e){
            System.out.println(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }


    public User retrieveLoggedInUser(){
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return (User) userService.loadUserByUsername(username);
    }
}
