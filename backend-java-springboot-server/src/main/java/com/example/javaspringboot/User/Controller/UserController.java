package com.example.javaspringboot.User.Controller;

import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.User.Records.RoleCredentialsRecord;
import com.example.javaspringboot.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//
//    @GetMapping()
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK); //ok is 200 status code
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserByID(@PathVariable("id") Long id) {
//        User attempt = userService.findUserById(id);
//        if (attempt != null){
//            return new ResponseEntity<>(attempt, HttpStatus.OK); //ok is 200 status code
//        }
//        return new ResponseEntity<>(attempt, HttpStatus.NOT_FOUND); //ok is 200 status code
//    }
//
//    @GetMapping("/getAllInRole")
//    public ResponseEntity<List<User>> getAllUsersInRole(@RequestBody Long roleId) {
//        List<User> users = userService.findByRoleContains(roleId);
//        return new ResponseEntity<>(users, HttpStatus.OK); //ok is 200 status code
//    }
//
//    @GetMapping("/getAllUserProfiles")
//    public ResponseEntity<List<UserProfile>> getAllUserProfiles() {
//        List<UserProfile> users = userService.findAllUserProfiles();
//        return new ResponseEntity<>(users, HttpStatus.OK); //ok is 200 status code
//    }
//
//    @PostMapping("/getUserProfileByEmail")
//    public ResponseEntity<UserProfile> getUserProfileByEmail(@RequestBody User user) {
//        UserProfile attempt = userService.findUserProfileUserByEmail(user.getCredentials().getCurrent_email());
//        if (attempt != null){ return new ResponseEntity<>(attempt, HttpStatus.OK); }
//        return new ResponseEntity<>(attempt, HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping("/getUserProfileAndStatsByEmail")
//    public ResponseEntity<UserProfileAndStats> getUserProfileAndStatsByEmail(@RequestBody User user) {
//        UserProfileAndStats attempt = userService.findUserProfileAndStatsByEmail(user.getEmail());
//        if (attempt != null){ return new ResponseEntity<>(attempt, HttpStatus.OK); }
//        return new ResponseEntity<>(attempt, HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping("/getUserProfileAndStatsById/{id}")
//    public ResponseEntity<UserProfileAndStats> getUserProfileAndStatsById(@PathVariable("id") Long id) {
//        UserProfileAndStats attempt = userService.findUserProfileAndStatsById(id);
//        if (attempt != null){ return new ResponseEntity<>(attempt, HttpStatus.OK); }
//        return new ResponseEntity<>(attempt, HttpStatus.NOT_FOUND);
//    }
//
//
//    @PostMapping("/getByEmail")
//    public ResponseEntity<User> getUserByEmail(@RequestBody User user) {
//        User attempt = userService.findUserByEmail(user.getEmail());
//        if (attempt != null){ return new ResponseEntity<>(attempt, HttpStatus.OK); }
//        return new ResponseEntity<>(attempt, HttpStatus.NOT_FOUND);
//    }
//
    @PostMapping("/role/add")
    public ResponseEntity<EnumResult> addRoleToUserByIds(@RequestBody RoleCredentialsRecord json) {
        return new ResponseEntity<>(userService.addRoleToUser(json.id(), json.role()), HttpStatus.OK);
    }

    @PostMapping("/role/remove")
    public ResponseEntity<EnumResult> removeRoleFromUserByIds(@RequestBody RoleCredentialsRecord json) {
        return new ResponseEntity<>(userService.removeRoleFromUser(json.id(), json.role()), HttpStatus.OK);
    }


//
//    @PutMapping("/initialRegister/{id}")
//    public ResponseEntity<?> addUser(@RequestBody InitialRegister initialRegister, @PathVariable("id") Long id) {
//        boolean result = false;
//        if (userService.initialRegister(initialRegister, id)){ return new ResponseEntity<>(result,HttpStatus.OK); }
//        else{ return new ResponseEntity<>(result,HttpStatus.NOT_MODIFIED); }
//    }
//
//    @PutMapping("/resetPassword")
//    public ResponseEntity<Boolean> resetUserPassword(@RequestBody String email) {
//        Boolean attempt = userService.resetUserPassword(email);
//        return new ResponseEntity<>(attempt, HttpStatus.OK);  //ok is 200 status code
//    }
//
//    @PutMapping("/updatePassword")
//    public ResponseEntity<Boolean> updateUserPassword(@RequestBody User user, String newPassword) {
//        Boolean attempt = userService.updatetUserPassword(user, newPassword);
//        return new ResponseEntity<>(attempt, HttpStatus.OK);  //ok is 200 status code
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<User> updateUser(@RequestBody User user) {
//        User updateUser = userService.updateUser(user);
//        return new ResponseEntity<>(updateUser, HttpStatus.OK);  //ok is 200 status code
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
