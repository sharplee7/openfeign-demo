package org.example.web;

import org.example.domain.Courtesy;
import org.example.domain.Greeting;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalleeController {
    @PostMapping("/courtesy")
    public Courtesy courtesy(@RequestBody Greeting greeting) throws Exception {
        Courtesy courtesy = new Courtesy();
        courtesy.setName("Hong gil dong");
        courtesy.setMessage("Thank you " + greeting.getName() + "\n" + "I'm " + courtesy.getName());

        return courtesy;
    }

}
