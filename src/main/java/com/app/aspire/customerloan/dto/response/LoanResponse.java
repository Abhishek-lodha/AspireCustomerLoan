package com.app.aspire.customerloan.dto.response;

import java.util.List;

import com.app.aspire.customerloan.entities.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class LoanResponse {
    private Long loanId;
    private LoanStatus status;
    private Double amount;
    private Integer term;
    private List<RepaymentResponse> repayments;
    
}
