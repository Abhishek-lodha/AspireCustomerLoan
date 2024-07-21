package com.app.aspire.customerloan.services;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.app.aspire.customerloan.dto.LoanDTO;
import com.app.aspire.customerloan.dto.response.LoanResponse;
import com.app.aspire.customerloan.dto.response.RepaymentResponse;
import com.app.aspire.customerloan.entities.Loan;
import com.app.aspire.customerloan.entities.Repayment;
import com.app.aspire.customerloan.entities.Users;
import com.app.aspire.customerloan.entities.enums.LoanStatus;
import com.app.aspire.customerloan.entities.enums.RepaymentStatus;
import com.app.aspire.customerloan.exceptions.ResourceNotFoundException;
import com.app.aspire.customerloan.repositories.LoanRepository;
import com.app.aspire.customerloan.repositories.RepaymentRepository;
import com.app.aspire.customerloan.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private LoanRepository loanRepository;

    private UserRepository userRepository;

    private RepaymentRepository repaymentRepository;

    LoanService(LoanRepository loanRepository,
            UserRepository userRepository,
            RepaymentRepository repaymentRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.repaymentRepository = repaymentRepository;

    }

    public LoanResponse createLoan(LoanDTO loanRequest, UserDetails userDetails) {
        Users customer = userRepository.findByUsername(userDetails.getUsername());
        Calendar cal = Calendar.getInstance();
        Loan loan = Loan.builder()
                .amount(loanRequest.getAmount())
                .term(loanRequest.getTerm())
                .customer(customer)
                .status(LoanStatus.PENDING)
                .startDate(cal.getTime())
                .build();
        loan = loanRepository.save(loan);

        double repaymentAmount = loan.getAmount() / loan.getTerm();
        cal.setTime(loan.getStartDate());

        for (int i = 0; i < loan.getTerm(); i++) {
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            Repayment repayment = Repayment.builder()
                    .loan(loan)
                    .date(cal.getTime())
                    .status(RepaymentStatus.PENDING)
                    .amount(i == loan.getTerm() - 1
                            ? repaymentAmount + loan.getAmount() % loan.getTerm()
                            : repaymentAmount)
                    .build();
            repaymentRepository.save(repayment);
        }

        return LoanResponse.builder().loanId(loan.getId()).status(loan.getStatus()).build();
    }

    public LoanResponse approveLoan(Long loanId) throws ResourceNotFoundException {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        loan.setStatus(LoanStatus.APPROVED);
        loanRepository.save(loan);
        return LoanResponse.builder()
                .loanId(loanId)
                .status(loan.getStatus())
                .build();
    }

    public List<LoanResponse> getLoansByCustomer(UserDetails userDetails) {
        Users customer = userRepository.findByUsername(userDetails.getUsername());
        List<Loan> customerLoans = loanRepository.findByCustomer(customer);
        List<LoanResponse> response = customerLoans.stream()
                .map(item -> {
                    List<RepaymentResponse> repayments = item.getRepayments().stream()
                            .map(repaymentItem -> RepaymentResponse.builder()
                                    .repaymentId(repaymentItem.getId())
                                    .amount(repaymentItem.getAmount())
                                    .status(repaymentItem.getStatus())
                                    .repaymentDate(repaymentItem.getDate())
                                    .repaidAmount(repaymentItem.getRepaidAmount())
                                    .build())
                            .collect(Collectors.toList());
                    return LoanResponse.builder()
                            .amount(item.getAmount())
                            .loanId(item.getId())
                            .status(item.getStatus())
                            .term(item.getTerm())
                            .repayments(repayments)
                            .build();
                })
                .collect(Collectors.toList());
        return response;
    }

}