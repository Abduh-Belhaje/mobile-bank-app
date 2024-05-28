package com.example.authservice.feign;

import com.example.authservice.dto.NotifRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("notification-service")
public interface NotifInterface {

    @PostMapping("/notif/send")
    public ResponseEntity<?> sendMessage(@RequestBody NotifRequest request);

}