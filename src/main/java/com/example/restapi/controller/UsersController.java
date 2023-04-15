package com.example.restapi.controller;

import com.example.restapi.model.Auth;
import com.example.restapi.model.Users;
import com.example.restapi.model.StatusController;
import com.example.restapi.model.Wallet;
import com.example.restapi.repo.AuthRepo;
import com.example.restapi.repo.UserRepo;
import com.example.restapi.repo.WalletRepo;
import net.minidev.json.JSONObject;
import java.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsersController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private WalletRepo walletRepo;
    StatusController status = new StatusController();


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Users>> getAllUsers(){
        try{
            List<Users> usersList = new ArrayList<>();
            userRepo.findAll().forEach(usersList::add);
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        } catch (Exception Ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        try {
            Optional<Users> users = userRepo.findById(id);
            if(users.isPresent()){
                Users userData = users.get();
                userData.setPassword(null);
                return new ResponseEntity<>(userData, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception Ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity Register(@RequestBody Users newUser){
        try{
            Optional<Users> userData = userRepo.findByEmail(newUser.getEmail());
            if(userData.isPresent()){
                String res;
                JSONObject json = new JSONObject();
                json.put("code", 202);
                json.put("message", "User exists");
                res = json.toString();
                return new ResponseEntity<>(json, HttpStatus.OK);
            }else{
                String password = newUser.getPassword();
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String passwordDigest = Arrays.toString(md.digest(password.getBytes()));
                Users user = new Users();
                user.setPassword(passwordDigest);
                user.setEmail(newUser.getEmail());
                user.setFullname(newUser.getFullname());
                Users userResponse = userRepo.save(user);
                Wallet userWallet = new Wallet();
                userWallet.setUserid(userResponse.getId());
                Wallet wallet = walletRepo.save(userWallet);
                return new ResponseEntity(status.Success(), HttpStatus.OK);
            }

        } catch(Exception Ex){
            return new ResponseEntity<>(status.MissingPayload(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity Login(@RequestBody Auth auth){

        try{
            Optional<Users> userData = userRepo.findByEmail(auth.getEmail());
            if(userData.isPresent()){
                //Password from DB
                String password = userData.get().getPassword();
//                String passWordToString = password.toString();

                //Password Input
                String loginPassword = auth.getPassword();

                //Convert to Byte

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String passwordDigest = Arrays.toString(md.digest(loginPassword.getBytes()));
//                String passwordString = passwordDigest;

                if(passwordDigest.equals(password)) {
                    java.util.Date loginTime = new Date();
                    JSONObject json = new JSONObject();
                    json.put("code", 200);
                    json.put("message", "Login Successful");
                    json.put("userid", userData.get().getId());
                    json.put("loginTime", loginTime);
                    Auth newAuth = new Auth();
                    newAuth.setLastLogin(loginTime);
                    newAuth.setEmail(auth.getEmail());
                    newAuth.setUserid(userData.get().getId());
                    newAuth.setPassword(password);
                    Auth auth1 = authRepo.save(newAuth);
                    return new ResponseEntity(json, HttpStatus.OK);
                }else {
                    JSONObject json = new JSONObject();
                    json.put("code", 202);
                    json.put("message", "Login Successful");
                    json.put("userid", userData.get().getId());
                    json.put("dbPassword", passwordDigest.toCharArray());
                    json.put("password", password.toCharArray());
                    return new ResponseEntity(json, HttpStatus.OK);
                }
            }else{
                String res;
                JSONObject json = new JSONObject();
                json.put("code", 202);
                json.put("message", "User with the email does not exists");
                res = json.toString();
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
        } catch(Exception Ex){
            return new ResponseEntity(Ex, HttpStatus.OK);
        }
    }

}
