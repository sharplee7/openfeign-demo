package org.example.web;

import org.example.domain.Courtesy;
import org.example.domain.Greeting;
import org.example.sync.CalleeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class CallerController {
    @Autowired
    CalleeFeignClient calleeFeignClient;

    @Value("${callee-service.url}")
    private String calleeUrl;


    @GetMapping("/greet/{name}")
    public Courtesy greet(@PathVariable("name") String name) throws Exception {

        System.out.println("====> CALLEE URL: " + calleeUrl);

        Greeting greeting = new Greeting();
        greeting.setName(name);
        greeting.setMessage("Hi " + name + ".\nNice meet you!");
        Courtesy courteies = calleeFeignClient.courtesy(greeting);
        return courteies;
    }
}
