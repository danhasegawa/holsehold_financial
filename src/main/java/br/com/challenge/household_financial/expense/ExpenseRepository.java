package br.com.challenge.household_financial.expense;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.challenge.household_financial.category.ExpenseByCategoryDto;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	@Query("select count(c) from Expense c where lower(c.description) = lower(:description) and month(c.date) = :month and year(c.date) = :year")
	Long expenseByDescription(String description, int month, int year);

	List<Expense> findByDescription(String escription);

	@Query("select c from Expense c where month(c.date) = :month and year(c.date) = :year")
	List<Expense> findByYearAndMonth(Integer year, Integer month);

	@Query("select sum(c.value) from Expense c where month(c.date) = :month and year(c.date) = :year")
	BigDecimal sumValuesByYearAndMonth(Integer year, Integer month);

	@Query(value = "select c.category, sum(c.value) as total from Expense c where month(c.date) = :month and year(c.date) = :year group by c.category", nativeQuery = true)
	List<ExpenseByCategoryDto> sumValueByCategory(Integer year, Integer month);
}