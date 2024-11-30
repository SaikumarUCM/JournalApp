package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){ return "ok";}




    @PostMapping("/create_user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}