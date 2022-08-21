package br.com.challenge.household_financial.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.com.challenge.household_financial.modelo.Category;
import br.com.challenge.household_financial.modelo.Expense;

public class ExpenseRequestDto {

	@NotNull
	private Long id;

	@NotNull
	private String description;

	@NotNull
	private BigDecimal value;

	@NotNull
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

	public void setCategory(Category category) {
		this.category = category;
	}

	public Expense update(Expense expense) {

		expense.setDescription(description);
		expense.setValue(value);
		expense.setDate(date);
		if (category == null) {
			expense.setCategory(Category.OTHER);
		} else {
			expense.setCategory(this.category);
		}
		return expense;
	}

	public Expense newEntity() {
		return Expense.newExpense(description, value, date, category);
	}

}