package com.customer.points.controller;

import com.customer.points.service.CustomerAwardService;
import com.customer.points.vo.RewardsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/rewards")
@Slf4j
public class CustomerAwardController {
    private final CustomerAwardService service;

    public CustomerAwardController(CustomerAwardService service) {
        this.service = service;
    }

    @GetMapping("/calculateRewards")
    public ResponseEntity<List<RewardsVO>> calculateRewardPoints(){
        log.info("Received request for calculateRewardPoints for all customers");
        List<RewardsVO> totalRewards = service.calculateRewardsPoints();
        log.info("successfully processed calculateRewardPoints");
        return ResponseEntity.ok(totalRewards);
    }

    @GetMapping("/calculateRewards/{customerId}")
    public ResponseEntity<RewardsVO> calculateRewardPointsByCustomer(@PathVariable("customerId") Long customerId){
        log.info("Received request for calculateRewardPointsByCustomer for customer : {}",customerId);
        RewardsVO totalRewards = service.calculateMonthlyRewardsPoints(customerId);
        log.info("successfully processed calculateMonthlyRewardsPoints");
        return ResponseEntity.ok(totalRewards);
    }
}
