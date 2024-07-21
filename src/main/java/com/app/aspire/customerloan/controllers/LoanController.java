package com.app.aspire.customerloan.controllers;

import java.util.List;

import com.app.aspire.customerloan.dto.LoanDTO;
import com.app.aspire.customerloan.dto.RepaymentRequest;
import com.app.aspire.customerloan.dto.response.LoanResponse;
import com.app.aspire.customerloan.dto.response.RepaymentResponse;
import com.app.aspire.customerloan.exceptions.LoanNotApprovedException;
import com.app.aspire.customerloan.exceptions.ResourceNotFoundException;
import com.app.aspire.customerloan.services.LoanService;
import com.app.aspire.customerloan.services.RepaymentService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoanController {
    private LoanService loanService;
    private RepaymentService repaymentService;

    LoanController(LoanService loanService, RepaymentService repaymentService) {
        this.loanService = loanService;
        this.repaymentService = repaymentService;
    }

    @PostMapping("/loans/new")
    public LoanResponse createLoan(@AuthenticationPrincipal UserDetails userDetails, @RequestBody LoanDTO loan) {
        return loanService.createLoan(loan, userDetails);
    }

    @PostMapping("/loans/{loanId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public LoanResponse approveLoan(@PathVariable Long loanId) throws ResourceNotFoundException {
        return loanService.approveLoan(loanId);
    }

    @GetMapping("/loans")
    public List<LoanResponse> getCustomerLoans(@AuthenticationPrincipal UserDetails userDetails) {
        return loanService.getLoansByCustomer(userDetails);
    }

    @PostMapping("/repayments")
    public RepaymentResponse addRepayment(@RequestBody RepaymentRequest repaymentRequest)
            throws ResourceNotFoundException, LoanNotApprovedException {
        return repaymentService.addRepayment(repaymentRequest);
    }
}