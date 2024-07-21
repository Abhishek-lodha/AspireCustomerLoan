package com.app.aspire.customerloan.entities;

import java.util.Date;

import com.app.aspire.customerloan.entities.enums.RepaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repayment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double amount;
    private Double repaidAmount;
    private RepaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
}