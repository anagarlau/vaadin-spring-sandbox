package org.vaadin.example.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vaadin.example.service.BroadcasterService;

@Controller
public class RestController {

    @PostMapping(value= "/response")
    public @ResponseBody
    ResponseEntity<Void> saySmth(){
        BroadcasterService.broadcast("hello");
        return ResponseEntity.ok().build();
    }
}
