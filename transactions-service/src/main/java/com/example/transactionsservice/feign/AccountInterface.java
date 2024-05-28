package com.example.transactionsservice.feign;

import com.example.transactionsservice.dto.UpdateBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("account-service")
public interface AccountInterface {

    @GetMapping("/account/Balance/{accountNb}")
    public ResponseEntity<?> getAccountBalance(@RequestParam("accountNb") Long accountNb );
    @PostMapping("/account/updateBalance")
    public ResponseEntity<?> updateBalance(@RequestBody UpdateBalanceRequest request);
}
