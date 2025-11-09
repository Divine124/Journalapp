package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

@Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

@PutMapping
    public ResponseEntity<?> updateuser(@RequestBody User user){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User userindb=userService.findByUsername(username);
         if (userindb!=null){
             userindb.setUsername(user.getUsername());
             userindb.setPassword(user.getPassword());
             userService.saveNewUser(userindb);

         }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@DeleteMapping
    public ResponseEntity<?>deleteUserById(){
    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    userRepository.deleteByUsername (authentication.getName());
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
    }
