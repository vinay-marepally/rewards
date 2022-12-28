package com.customer.points.service;

import com.customer.points.model.Transactions;
import com.customer.points.repo.TransactionRepository;
import com.customer.points.vo.MonthlyRewardsVO;
import com.customer.points.vo.RewardsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerAwardService {

    private final TransactionRepository repository;

    public CustomerAwardService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<RewardsVO> calculateRewardsPoints(){
        log.info("started calculateRewardsPoints");
        Set<Long> customerIds = repository.findAllCustomerIds();
        List<RewardsVO> rewardsVOS = new ArrayList<>();
        for(Long customerId : customerIds){
            RewardsVO rewardsVO = calculateMonthlyRewardsPoints(customerId);
            rewardsVO.setCustomerId(customerId);
            rewardsVOS.add(rewardsVO);
        }
        log.info("end calculateRewardsPoints");
        return rewardsVOS;
    }

    public RewardsVO calculateMonthlyRewardsPoints(Long customerId) {
        log.info("started calculateMonthlyRewardsPoints");
        List<Transactions> transactions = repository.findAllByCustomerId(customerId);
        Map<String,Integer> rewards = transactions.stream().
                    collect(Collectors.toMap(t-> t.getDate().getMonth().name(),t-> calculateMonthlyRewardsPoints(t.getAmount()),Integer::sum));

        RewardsVO rewardsVO = new RewardsVO();
        rewardsVO.setCustomerId(customerId);
        rewardsVO.setMonthlyRewards(new ArrayList<>());
        rewards.forEach((key,value)->{
            MonthlyRewardsVO monthlyRewardsVO = new MonthlyRewardsVO();
            monthlyRewardsVO.setTotalRewards(value);
            monthlyRewardsVO.setMonth(key);
            rewardsVO.getMonthlyRewards().add(monthlyRewardsVO);
        });

        rewardsVO.setTotalRewards(rewardsVO.getMonthlyRewards().stream().mapToInt(MonthlyRewardsVO::getTotalRewards).sum());
        log.info("end calculateMonthlyRewardsPoints");
        return rewardsVO;
    }

    private int calculateMonthlyRewardsPoints(double amount) {
        return calculateRewardsPointsAbove100(amount)+calculateRewardsPointsAbove50(amount);
    }

    private int calculateRewardsPointsAbove50(double amount) {
        if(amount>100) amount = 100;
        amount = amount-50;
        if(amount > 0){
            return (int) amount;
        }
        return 0;
    }

    private int calculateRewardsPointsAbove100(double amount) {
        amount = amount - 100;
        if(amount > 0){
            return (int) (amount*2);
        }
        return 0;
    }
}
