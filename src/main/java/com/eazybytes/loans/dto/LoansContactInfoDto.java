package com.eazybytes.loans.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Component
@ConfigurationProperties(prefix = "loans")
public class LoansContactInfoDto {
	
	private String message;
	private Map<String, String> contactDetails;
	private List<String> onCallSupport;

}
