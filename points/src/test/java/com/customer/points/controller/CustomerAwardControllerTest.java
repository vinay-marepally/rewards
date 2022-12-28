package com.customer.points.controller;

import com.customer.points.service.CustomerAwardService;
import com.customer.points.vo.RewardsVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAwardControllerTest {

    @Mock
    CustomerAwardService service;

    @InjectMocks
    CustomerAwardController controller;
    @Test
    void calculateRewardPoints() {
        List<RewardsVO> totalRewards = new ArrayList<>();
        when(service.calculateRewardsPoints()).thenReturn(totalRewards);
        ResponseEntity<List<RewardsVO>> responseEntity = controller.calculateRewardPoints();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void calculateRewardPointsByCustomer() {
        RewardsVO rewardsVO = new RewardsVO();
        when(service.calculateMonthlyRewardsPoints(anyLong())).thenReturn(rewardsVO);
        ResponseEntity<RewardsVO> responseEntity = controller.calculateRewardPointsByCustomer(1L);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}