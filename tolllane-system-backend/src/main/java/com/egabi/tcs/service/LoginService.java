package com.egabi.tcs.service;

import com.egabi.tcs.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    public List<User> appUsers(){
        return List.of(new User("mohamed","1234"),new User("atef","12345"));
    }

    public boolean login(User user){
        return appUsers().stream().anyMatch(userObj -> userObj.getUsername().toLowerCase().equalsIgnoreCase(user.getUsername())
                && userObj.getPassword().toLowerCase().equalsIgnoreCase(user.getPassword()));
    }

    public boolean logout(){
        return true;
    }
}
