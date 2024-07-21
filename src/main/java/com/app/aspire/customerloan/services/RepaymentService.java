package com.app.aspire.customerloan.services;

import com.app.aspire.customerloan.dto.RepaymentRequest;
import com.app.aspire.customerloan.dto.response.RepaymentResponse;
import com.app.aspire.customerloan.entities.Loan;
import com.app.aspire.customerloan.entities.Repayment;
import com.app.aspire.customerloan.entities.enums.LoanStatus;
import com.app.aspire.customerloan.entities.enums.RepaymentStatus;
import com.app.aspire.customerloan.exceptions.LoanNotApprovedException;
import com.app.aspire.customerloan.exceptions.ResourceNotFoundException;
import com.app.aspire.customerloan.repositories.LoanRepository;
import com.app.aspire.customerloan.repositories.RepaymentRepository;

import org.springframework.stereotype.Service;

@Service
public class RepaymentService {
    private LoanRepository loanRepository;

    private RepaymentRepository repaymentRepository;

    RepaymentService(LoanRepository loanRepository,
            RepaymentRepository repaymentRepository) {
        this.loanRepository = loanRepository;
        this.repaymentRepository = repaymentRepository;

    }

    public RepaymentResponse addRepayment(RepaymentRequest repaymentRequest)
            throws ResourceNotFoundException, LoanNotApprovedException {
        Repayment repayment = repaymentRepository.findById(repaymentRequest.getRepaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Repayment not found"));

        if (repayment.getLoan().getStatus() != LoanStatus.APPROVED) {
            throw new LoanNotApprovedException("Loan is not yet approved");
        }

        if (repaymentRequest.getAmount() >= repayment.getAmount()) {
            repayment.setStatus(RepaymentStatus.PAID);
            repaymentRepository.save(repayment);
            checkAndUpdateLoanStatus(repayment);
        }
        repayment.setRepaidAmount(repaymentRequest.getAmount());
        return RepaymentResponse.builder()
                .repaymentId(repayment.getId())
                .status(repayment.getStatus())
                .build();
    }

    private void checkAndUpdateLoanStatus(Repayment repayment) {
        Loan loan = repayment.getLoan();
        boolean allPaid = loan.getRepayments().stream()
                .allMatch(r -> r.getStatus().equals(RepaymentStatus.PAID));
        if (allPaid) {
            loan.setStatus(LoanStatus.PAID);
            loanRepository.save(loan);
        }
    }
}
