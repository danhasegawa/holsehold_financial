package br.com.challenge.household_financial.monthly;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.challenge.household_financial.category.Category;

public class MonthlySummaryResponseDto {

	private Integer year;
	private Integer month;
	private BigDecimal totalIncomes;
	private BigDecimal totalExpenses;
	private List<MonthlySummaryCategoryResponseDto> totalByCategory = new ArrayList<>();

	public Integer getYear() {
		return year;
	}

	public Integer getMonth() {
		return month;
	}

	public BigDecimal getTotalIncomes() {
		return totalIncomes;
	}

	public BigDecimal getTotalExpenses() {
		return totalExpenses;
	}

	public List<MonthlySummaryCategoryResponseDto> getTotalByCategory() {
		return Collections.unmodifiableList(totalByCategory);
	}

	public void addTotalByCategory(Category category, BigDecimal total) {
		this.totalByCategory.add(MonthlySummaryCategoryResponseDto.summaryCategory(category, total));
	}

	public BigDecimal getBalance() {
		return this.totalIncomes.subtract(totalExpenses);
	}

	public static MonthlySummaryResponseDto summaryDto(Integer year, Integer month, BigDecimal totalIncomes,
			BigDecimal totalExpenses) {
		MonthlySummaryResponseDto dto = new MonthlySummaryResponseDto();
		dto.year = year;
		dto.month = month;
		dto.totalIncomes = totalIncomes;
		dto.totalExpenses = totalExpenses;
		return dto;

	}

}