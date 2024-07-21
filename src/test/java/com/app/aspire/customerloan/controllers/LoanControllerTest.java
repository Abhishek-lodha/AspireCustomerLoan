package com.app.aspire.customerloan.controllers;

import com.app.aspire.customerloan.dto.LoanDTO;
import com.app.aspire.customerloan.dto.response.LoanResponse;
import com.app.aspire.customerloan.entities.Loan;
import com.app.aspire.customerloan.entities.Repayment;
import com.app.aspire.customerloan.entities.enums.LoanStatus;
import com.app.aspire.customerloan.services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LoanController loanController;

    @MockBean
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
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
    void createLoan() throws Exception {
        LoanResponse loan = LoanResponse.builder().amount((double) 1000).term(2).build();


        when(loanService.createLoan(any(LoanDTO.class), any())).thenReturn(loan);

        mockMvc.perform(post("/api/loans/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": 1000, \"term\": 2, \"state\": \"PENDING\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000));
    }

    @Test
    void getAllLoans() throws Exception {
        LoanResponse loan1 = new LoanResponse();
        LoanResponse loan2 = new LoanResponse();

        when(loanService.getLoansByCustomer(any())).thenReturn(Arrays.asList(loan1, loan2));

        mockMvc.perform(get("/api/loans")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // @Test
    // void addRepayment() throws Exception {
    //     Loan loan = new Loan();
    //     loan.setId(1L);

    //     Repayment repayment = new Repayment();
    //     repayment.setAmount(3333.33);
    //     repayment.setState("PENDING");
    //     repayment.setLoan(loan);

    //     when(loanService.getLoanById(1L)).thenReturn(loan);
    //     when(loanService.addRepayment(any(Repayment.class))).thenReturn(repayment);

    //     mockMvc.perform(post("/api/repayments")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("{\"amount\": 3333.33, \"state\": \"PENDING\"}"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.amount").value(3333.33));
    // }

}