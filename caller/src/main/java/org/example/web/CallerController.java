package org.example.web;

import org.example.domain.Courtesy;
import org.example.domain.Greeting;
import org.example.sync.CalleeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CallerController {
    @Autowired
    CalleeFeignClient calleeFeignClient;

    @GetMapping("/greet/{name}")
    public Courtesy greet(@PathVariable("name") String name) throws Exception {
        Greeting greeting = new Greeting();
        greeting.setName(name);
        greeting.setMessage("Hi " + name + ".\nNice meet you!");
        Courtesy courteies = calleeFeignClient.courtesy(greeting);
        return courteies;
    }
}
