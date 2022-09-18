package br.com.challenge.household_financial.income;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	@Query("select count(c) from Income c where lower(c.description) = lower(:description) and month(c.date) = :month and year(c.date) = :year")
	Long incomeByDescription(String description,  int month, int year);

	List<Income> findByDescription(String description);
	
	@Query("select c from Income c where month(c.date) = :month and year(c.date) = :year")
	List<Income> findByYearAndMonth(Integer year, Integer month);
	
	@Query("select sum(c.value) from Income c where month(c.date) = :month and year(c.date) = :year")
	BigDecimal sumValuesByYearAndMonth(Integer year, Integer month);
}