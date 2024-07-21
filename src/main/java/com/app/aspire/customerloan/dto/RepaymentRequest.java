package com.app.aspire.customerloan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentRequest {
    private Long repaymentId;
    private Double amount;
    
}
