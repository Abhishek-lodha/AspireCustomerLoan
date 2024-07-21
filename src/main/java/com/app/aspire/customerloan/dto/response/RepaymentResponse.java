package com.app.aspire.customerloan.dto.response;

import java.util.Date;

import com.app.aspire.customerloan.entities.enums.RepaymentStatus;
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
public class RepaymentResponse {

    private Long repaymentId;
    private RepaymentStatus status;
    private Double amount;
    private Double repaidAmount;
    private Date repaymentDate;
    
}
