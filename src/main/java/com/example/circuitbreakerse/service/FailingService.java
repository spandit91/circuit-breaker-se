package com.example.circuitbreakerse.service;
 
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Optional;
 
@Service
public class FailingService  {
    
    public Mono<String> greet(Optional<String> name) {
        return name.map(inputName -> Mono.just("Hello " + inputName + "!")).orElse(Mono.error(new NullPointerException()));
    }
 
}