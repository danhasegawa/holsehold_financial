package br.com.challenge.household_financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.challenge.household_financial.modelo.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	@Query("select count(c) from Income c where lower(c.description) = lower(:description) and month(c.date) = :month and year(c.date) = :year")
	Long incomeByDescription(String description, int month, int year);
}