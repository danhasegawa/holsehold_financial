package br.com.challenge.household_financial.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.challenge.household_financial.modelo.Category;
import br.com.challenge.household_financial.modelo.Expense;


public class ExpenseRequestDto {

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
	
	private Category category;

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
	
	public Expense update(Expense expense) {

		expense.setDescription(description);
		expense.setValue(value);
		expense.setDate(date);
		expense.setCategory(category);
		return expense;
	}
	
	public Expense newEntity() {
		return Expense.newExpense(description, value,date, category);
	}

}