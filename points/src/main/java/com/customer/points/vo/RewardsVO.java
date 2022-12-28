package com.customer.points.vo;

import lombok.Data;

import java.util.List;

@Data
public class RewardsVO {
    private Long customerId;
    private int totalRewards;
    private List<MonthlyRewardsVO> monthlyRewards;
}
