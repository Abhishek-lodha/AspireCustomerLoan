package com.app.aspire.customerloan.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanDTO {
    private Double amount;
    private Integer term;
}
