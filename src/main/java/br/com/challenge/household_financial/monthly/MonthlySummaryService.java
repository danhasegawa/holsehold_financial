package br.com.challenge.household_financial.monthly;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.household_financial.category.ExpenseByCategoryDto;
import br.com.challenge.household_financial.expense.ExpenseRepository;
import br.com.challenge.household_financial.income.IncomeRepository;

@Service
public class MonthlySummaryService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	public MonthlySummaryResponseDto getSummary(Integer year, Integer month) {

		BigDecimal totalIncome = incomeRepository.sumValuesByYearAndMonth(year, month);
		if (totalIncome == null)
			totalIncome = BigDecimal.ZERO;

		BigDecimal totalExpense = expenseRepository.sumValuesByYearAndMonth(year, month);
		if (totalExpense == null)
			totalExpense = BigDecimal.ZERO;

		MonthlySummaryResponseDto result = MonthlySummaryResponseDto.summaryDto(year, month, totalIncome, totalExpense);

		List<ExpenseByCategoryDto> sumValueByCategory = expenseRepository.sumValueByCategory(year, month);
		
		sumValueByCategory.stream().forEach(e -> result.addTotalByCategory(e.getCategory(), e.getTotal()));
		return result;
		
	}

}
