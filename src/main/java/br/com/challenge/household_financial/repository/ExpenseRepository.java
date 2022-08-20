package br.com.challenge.household_financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.challenge.household_financial.modelo.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	@Query("select count(c) from Expense c where lower(c.description) = lower(:description) and month(c.date) = :month and year(c.date) = :year")
	Long expenseByDescription(String description, int month, int year);

}