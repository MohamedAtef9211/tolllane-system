package com.egabi.tls.service;

import com.egabi.tls.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomService {

    public String getMessage(){
        return "hello from spring";
    }

    public List<User> appUsers(){
        return List.of(new User("mohamed","1234"),new User("atef","12345"));
    }
}
