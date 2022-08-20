package br.com.challenge.household_financial.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.challenge.household_financial.modelo.Category;
import br.com.challenge.household_financial.modelo.Expense;

public class ExpenseResponseDto {

	private Long id;
	private String description;
	private BigDecimal value;
	private LocalDate date;
	private Category category;

	public ExpenseResponseDto(Expense expense) {
		this.id = expense.getId();
		this.description = expense.getDescription();
		this.value = expense.getValue();
		this.date = expense.getDate();
		this.category = expense.getCategory();

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

	public Category getCategory() {
		return category;
	}

}