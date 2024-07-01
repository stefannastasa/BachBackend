package org.handnotes.controllers;


import jakarta.annotation.Nonnull;
import org.handnotes.auth.AuthenticationService;
import org.handnotes.model.requests.LoginRequest;
import org.handnotes.model.requests.LogoutRequest;
import org.handnotes.model.requests.RegisterRequest;
import org.handnotes.model.responses.IResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    public AuthController(AuthenticationService authenticationHandler){
        this.authenticationService = authenticationHandler;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<IResponse> register(@RequestBody RegisterRequest registerRequest){
        System.out.println("Registering...");

        return authenticationService.registerFlow(registerRequest);
    }

    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<IResponse> login(@RequestBody LoginRequest loginReq){
        System.out.println("Logging in... " + loginReq.getUsername());
        return authenticationService.authFlow(loginReq);
    }

    @ResponseBody
    @RequestMapping(value="/health", method = RequestMethod.GET)
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("Application healthy.", HttpStatus.ACCEPTED);
    }


}
