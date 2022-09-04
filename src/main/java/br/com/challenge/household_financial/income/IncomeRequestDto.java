package br.com.challenge.household_financial.income;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class IncomeRequestDto {

	@NotNull
	private Long id;

	@NotNull
	private String description;

	@NotNull
	private BigDecimal value;

	@NotNull
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Income update(Income income) {
		income.setDescription(description);
		income.setValue(value);
		income.setDate(date);
		return income;

	}

}