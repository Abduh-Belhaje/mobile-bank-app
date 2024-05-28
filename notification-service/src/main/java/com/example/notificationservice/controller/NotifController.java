package com.example.notificationservice.controller;


import com.example.notificationservice.dto.NotifRequest;
import com.example.notificationservice.exception.MessageSendingException;
import com.example.notificationservice.service.NotifService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/notif")
public class NotifController {

    private NotifService notifService;
    private Environment env;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody NotifRequest request){
        try {
            notifService.sendMessageToClient(
                    request,
                    env.getProperty("Url"),
                    env.getProperty("User-Access-Token")
            );
            return ResponseEntity.ok().build();
        }catch (MessageSendingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("a problem was accured");
        }
    }


    @GetMapping
    public String test(){
        return "reached";
    }



}
