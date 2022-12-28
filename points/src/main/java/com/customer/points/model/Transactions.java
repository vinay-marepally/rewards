package com.customer.points.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Transactions {

    @Id
    private long id;
    @Column(name = "customerId")
    private Long customerId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "date")
    private LocalDateTime date;
}
