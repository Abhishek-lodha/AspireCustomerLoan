package com.app.aspire.customerloan.services;

import com.app.aspire.customerloan.dto.RepaymentRequest;
import com.app.aspire.customerloan.dto.response.RepaymentResponse;
import com.app.aspire.customerloan.entities.Loan;
import com.app.aspire.customerloan.entities.Repayment;
import com.app.aspire.customerloan.entities.enums.RepaymentStatus;
import com.app.aspire.customerloan.exceptions.LoanNotApprovedException;
import com.app.aspire.customerloan.exceptions.ResourceNotFoundException;
import com.app.aspire.customerloan.repositories.RepaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RepaymentServiceTests {

    @InjectMocks
    private RepaymentService repaymentService;

    @Mock
    private RepaymentRepository repaymentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRepayment() throws ResourceNotFoundException,
            LoanNotApprovedException {
        Loan loan = new Loan();
        loan.setId(1L);

        RepaymentRequest repaymentRequest = new RepaymentRequest();
        repaymentRequest.setAmount(3333.33);

        Repayment repayment = new Repayment();
        repayment.setAmount(3333.33);
        repayment.setStatus(RepaymentStatus.PAID);
        repayment.setId(1L);

        when(repaymentRepository.save(any(Repayment.class))).thenReturn(repayment);

        RepaymentResponse createdRepayment = repaymentService.addRepayment(repaymentRequest);

        assertNotNull(createdRepayment);
        assertEquals(3333.33, createdRepayment.getAmount());
    }

}
