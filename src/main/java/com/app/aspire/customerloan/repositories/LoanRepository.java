package com.app.aspire.customerloan.repositories;

import java.util.List;

import com.app.aspire.customerloan.entities.Loan;
import com.app.aspire.customerloan.entities.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long>  {
    List<Loan> findByCustomer(Users customer);
}
