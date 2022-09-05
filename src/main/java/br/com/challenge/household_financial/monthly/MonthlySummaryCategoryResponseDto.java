package br.com.challenge.household_financial.monthly;

import java.math.BigDecimal;

import br.com.challenge.household_financial.category.Category;

public class MonthlySummaryCategoryResponseDto {
	
	private Category category;
	private BigDecimal total;
	
	public Category getCategory() {
		return category;
	}
	public BigDecimal getTotal() {
		return total;
	}

	public static MonthlySummaryCategoryResponseDto summaryCategory(Category category, BigDecimal total) {
		MonthlySummaryCategoryResponseDto dto = new MonthlySummaryCategoryResponseDto();
		dto.category = category;
		dto.total = total;
		return dto;
		
	}
}
