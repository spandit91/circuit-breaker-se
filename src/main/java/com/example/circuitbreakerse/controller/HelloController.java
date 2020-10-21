package com.example.circuitbreakerse.controller;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import reactor.core.publisher.Mono;
import java.util.function.Function;
 
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
 
 
import com.example.circuitbreakerse.service.FailingService;
 
@RestController
public class HelloController {
 
 
    final FailingService failingService;
    final ReactiveCircuitBreaker rcb;
 
    public HelloController(FailingService failingService, ReactiveCircuitBreakerFactory rcbf) {
        this.failingService = failingService;
        this.rcb = rcbf.create("greet");
    }
 
 
    @GetMapping("/hello")
    public Mono<String> hello(@RequestParam Optional<String> name){
        Mono<String> results =  this.failingService.greet(name);
        return this.rcb.run(results, new Function<Throwable, Mono<String>>(){
            public Mono<String> apply (Throwable throwable) {
                return Mono.just("Hello NoName");
            }
        });
    }
    
}