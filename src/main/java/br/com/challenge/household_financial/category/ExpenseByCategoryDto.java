package br.com.challenge.household_financial.category;

import java.math.BigDecimal;

public interface ExpenseByCategoryDto {

	public Category getCategory();

	public BigDecimal getTotal();
}
