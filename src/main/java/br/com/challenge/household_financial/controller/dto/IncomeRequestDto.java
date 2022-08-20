package br.com.challenge.household_financial.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.challenge.household_financial.modelo.Income;

public class IncomeRequestDto {

	@NotNull
	@NotEmpty
	private Long id;

	@NotNull
	@NotEmpty
	private String description;

	@NotNull
	@NotEmpty
	private BigDecimal value;

	@NotNull
	@NotEmpty
	private LocalDate date;

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

	public Income update(Income income) {
		income.setDescription(description);
		income.setValue(value);
		income.setDate(date);
		return income;

	}

}