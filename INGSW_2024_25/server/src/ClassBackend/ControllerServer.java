package Classbackend;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ControllerServer {
    @GetMapping("/hello")
    public String sayHello() {
        return "Ciao dal backend!";
    }
}