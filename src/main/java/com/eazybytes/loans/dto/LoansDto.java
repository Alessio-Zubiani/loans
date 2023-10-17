package com.eazybytes.loans.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Loans",
	description = "Schema to hold Loan information"
)
@Data
public class LoansDto {
	
	@Schema(
            description = "MobileNumber of Customer", example = "4365327698"
    )
	@NotEmpty(message = "MobileNumber can not be null or empty")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

	@Schema(
            description = "LoanNumber of the customer", example = "548732457654"
    )
    @NotEmpty(message = "LoanNumber can not be null or empty")
    @Pattern(regexp="(^$|[0-9]{12})", message = "LoanNumber must be 12 digits")
    private String loanNumber;

    @Schema(
            description = "Type of the loan", example = "Home Loan"
    )
    @NotEmpty(message = "LoanType can not be null or empty")
    private String loanType;

    @Schema(
            description = "Total loan amount", example = "100000"
    )
    @Positive(message = "Total loan amount should be greater than zero")
    private int totalLoan;

    @Schema(
            description = "Total loan amount paid", example = "1000"
    )
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    private int amountPaid;

    @Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    private int outstandingAmount;

}
