package com.app.aspire.customerloan.repositories;

import com.app.aspire.customerloan.entities.Repayment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
}