package com.app.aspire.customerloan.services;

import com.app.aspire.customerloan.dto.LoanDTO;
import com.app.aspire.customerloan.dto.RepaymentRequest;
import com.app.aspire.customerloan.dto.response.LoanResponse;
import com.app.aspire.customerloan.dto.response.RepaymentResponse;
import com.app.aspire.customerloan.entities.Loan;
import com.app.aspire.customerloan.entities.Repayment;
import com.app.aspire.customerloan.entities.enums.LoanStatus;
import com.app.aspire.customerloan.entities.enums.RepaymentStatus;
import com.app.aspire.customerloan.exceptions.LoanNotApprovedException;
import com.app.aspire.customerloan.exceptions.ResourceNotFoundException;
import com.app.aspire.customerloan.repositories.LoanRepository;
import com.app.aspire.customerloan.repositories.RepaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private RepaymentRepository repaymentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserDetails setUpMockUser(String username, String role) {
        UserDetails user = User.withUsername(username)
                .password("randomPassword")
                .authorities(new SimpleGrantedAuthority(role))
                .build();
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user, user.getPassword(), user.getAuthorities()));
        return user;
    }

    @Test
    void createLoan() {
        LoanDTO loanRequest = LoanDTO.builder().amount((double) 1000).term(2).build();
        Loan loan = new Loan();
        loan.setAmount((double) 1000);
        loan.setTerm(2);
        loan.setStatus(LoanStatus.PENDING);

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanResponse createdLoan = loanService.createLoan(loanRequest, setUpMockUser("user1", "ROLE_CUSTOMER"));

        assertNotNull(createdLoan);
        assertEquals(10000, createdLoan.getAmount());
        assertEquals(2, createdLoan.getRepayments().size());
        assertEquals(LoanStatus.PENDING, createdLoan.getStatus());
    }

    @Test
    void getAllLoans() {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();

        when(loanRepository.findByCustomer(any())).thenReturn(Arrays.asList(loan1, loan2));

        List<LoanResponse> loans = loanService.getLoansByCustomer(setUpMockUser("user1", "ROLE_CUSTOMER"));

        assertEquals(2, loans.size());
    }
}