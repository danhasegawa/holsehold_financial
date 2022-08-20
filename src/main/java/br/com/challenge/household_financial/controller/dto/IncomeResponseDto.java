package br.com.challenge.household_financial.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.challenge.household_financial.modelo.Income;

public class IncomeResponseDto {

	private Long id;
	private String description;
	private BigDecimal value;
	private LocalDate date;

	public IncomeResponseDto(Income income) {
		this.id = income.getId();
		this.description = income.getDescription();
		this.value = income.getValue();
		this.date = income.getDate();

	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public LocalDate getDate() {
		return date;
	}

}
