package org.example.sync;

import feign.hystrix.FallbackFactory;
import org.example.domain.Courtesy;
import org.example.domain.Greeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "callee-service",
        url = "http://localhost:8082",
        fallbackFactory = CalleeFeignClientFallbackFactory.class)
public interface CalleeFeignClient {
    @GetMapping("/courtesy")
    public Courtesy courtesy(@RequestBody Greeting greeting) throws Exception;
}

@Component
class CalleeFeignClientFallbackFactory implements FallbackFactory<CalleeFeignClient> {
    @Override
    public CalleeFeignClient create(Throwable t) {
        return new CalleeFeignClient() {

            @Override
            public Courtesy courtesy(@RequestBody Greeting greeting) throws Exception {
                System.out.println("************************");
                t.printStackTrace();
                return null;
            }

        };
    }

}