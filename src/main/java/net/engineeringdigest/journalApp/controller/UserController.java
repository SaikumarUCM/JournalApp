package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.model.JournalEntry;
import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private WeatherService weatherService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDB = userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse response= weatherService.getWeather("Tampa");

        log.info("response: {} ",response);
        if(response.getCurrent() == null){
            log.error("Current Weather data is missing for city ");
        }
        String greetings="";
        if(response!=null){
            greetings=", Temperature  is  "+ response.getCurrent().getTemperature()+" degrees Celsius";
        }
        return new ResponseEntity<>("Hi " + authentication.getName()+greetings,HttpStatus.OK);
    }

}
