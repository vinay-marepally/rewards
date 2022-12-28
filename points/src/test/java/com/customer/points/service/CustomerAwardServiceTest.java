package com.customer.points.service;

import com.customer.points.model.Transactions;
import com.customer.points.repo.TransactionRepository;
import com.customer.points.vo.MonthlyRewardsVO;
import com.customer.points.vo.RewardsVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAwardServiceTest {

    @Mock
    TransactionRepository repository;
    @InjectMocks
    CustomerAwardService service;
    @Test
    void calculateRewardsPoints_allCustomers() {
        Set<Long> customerIds = new HashSet<>();
        customerIds.add(1L);
        customerIds.add(2L);

        when(repository.findAllCustomerIds()).thenReturn(customerIds);
        List<Transactions> cust1Trans = new ArrayList<>();
        Transactions transactions1 = Transactions.builder()
                .customerId(1L).date(LocalDateTime.now()).amount(120).build();
        cust1Trans.add(transactions1);

        List<Transactions> cust2Trans = new ArrayList<>();
        Transactions transactions2 = Transactions.builder()
                .customerId(2L).date(LocalDateTime.now()).amount(90).build();
        cust2Trans.add(transactions2);
        when(repository.findAllByCustomerId(1L)).thenReturn(cust1Trans);
        when(repository.findAllByCustomerId(2L)).thenReturn(cust2Trans);

        List<RewardsVO> rewardsVOS = service.calculateRewardsPoints();

        assertFalse(rewardsVOS.isEmpty());
        assertEquals(2,rewardsVOS.size());
        assertEquals(90,rewardsVOS.get(0).getTotalRewards());
        assertEquals(40,rewardsVOS.get(1).getTotalRewards());

    }

    @Test
    void calculateMonthlyRewardsPoints() {
        List<Transactions> cust1Trans = new ArrayList<>();
        Transactions transactions1 = Transactions.builder()
                .customerId(1L).date(LocalDateTime.now()).amount(120).build();
        Transactions transactions2 = Transactions.builder()
                .customerId(1L).date(LocalDateTime.now().minusDays(30)).amount(90).build();
        cust1Trans.add(transactions1);
        cust1Trans.add(transactions2);
        when(repository.findAllByCustomerId(anyLong())).thenReturn(cust1Trans);

        RewardsVO rewardsVO = service.calculateMonthlyRewardsPoints(1L);
        assertEquals(130,rewardsVO.getTotalRewards());
        assertFalse(rewardsVO.getMonthlyRewards().isEmpty());
        List<MonthlyRewardsVO> monthlyRewardsVOS = rewardsVO.getMonthlyRewards();
        assertEquals(2,monthlyRewardsVOS.size());
        assertEquals(90,monthlyRewardsVOS.get(0).getTotalRewards());
        assertEquals(40,monthlyRewardsVOS.get(1).getTotalRewards());
    }

    @Test
    void calculateMonthlyRewardsPoints_zer0Rewards() {
        List<Transactions> cust1Trans = new ArrayList<>();
        Transactions transactions1 = Transactions.builder()
                .customerId(1L).date(LocalDateTime.now()).amount(40).build();
        cust1Trans.add(transactions1);
        when(repository.findAllByCustomerId(anyLong())).thenReturn(cust1Trans);

        RewardsVO rewardsVO = service.calculateMonthlyRewardsPoints(1L);
        assertEquals(0,rewardsVO.getTotalRewards());
    }
}