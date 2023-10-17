package com.eazybytes.loans.service.impl;

import java.util.Optional;
import java.util.Random;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements ILoansService {
	
	private final LoansRepository loansRepository;
	

	@Override
	public void createLoan(String mobileNumber) {
		
		Optional<Loans> optionalLoans= this.loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
        	throw new LoanAlreadyExistsException(
					new StringBuilder("Loans with mobile number [").append(mobileNumber)
						.append("] already registered").toString());
        }
        
        this.loansRepository.save(this.createNewLoan(mobileNumber));
	}

	@Override
	public LoansDto fetchLoan(String mobileNumber) {
		
		Loans loans = this.loansRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
		
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
	}

	@Override
	public boolean updateLoan(LoansDto loansDto) {
		
		Loans loans = this.loansRepository.findByLoanNumber(loansDto.getLoanNumber())
				.orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        this.loansRepository.save(loans);
        
        return true;
	}

	@Override
	public boolean deleteLoan(String mobileNumber) {
		
		Loans loans = this.loansRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        this.loansRepository.deleteById(loans.getLoanId());
        
        return true;
	}
	
	/**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
    	
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        
        return newLoan;
    }

}
