package br.com.challenge.household_financial.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.challenge.household_financial.category.Category;
import br.com.challenge.household_financial.category.ExpenseByCategoryDto;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ExpenseRepositoryTest {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void shouldReturnExpenseByDescription() {

		@SuppressWarnings("unused")
		Expense testExpense = testEntityManager
				.persist(Expense.newExpense("Grocery", new BigDecimal(500), LocalDate.of(2022, 9, 5), Category.FOOD));

		Long expense = expenseRepository.expenseByDescription("Grocery", 9, 2022);
		Assert.assertNotNull(expense);

	}

	@Test
	public void shouldReturnOkFindingByDescription() {

		@SuppressWarnings("unused")
		Expense testExpense = testEntityManager
				.persist(Expense.newExpense("Grocery", new BigDecimal(500), LocalDate.of(2022, 9, 5), Category.FOOD));

		@SuppressWarnings("unused")
		Expense testExpense2 = testEntityManager
				.persist(Expense.newExpense("Grocery", new BigDecimal(50), LocalDate.of(2022, 9, 10), Category.FOOD));

		List<Expense> expenses = expenseRepository.findByDescription("Grocery");
		Assert.assertNotNull(expenses);
		Assert.assertEquals(2, expenses.size());
	}

	@Test
	public void shouldReturnOkFindingByYearAndMonty() {

		@SuppressWarnings("unused")
		Expense testExpense = testEntityManager
				.persist(Expense.newExpense("Rent", new BigDecimal(1500), LocalDate.of(2022, 9, 1), Category.HOUSING));

		@SuppressWarnings("unused")
		Expense testExpense2 = testEntityManager
				.persist(Expense.newExpense("Grocery", new BigDecimal(50), LocalDate.of(2022, 8, 10), Category.FOOD));

		List<Expense> expenses = expenseRepository.findByYearAndMonth(2022, 9);
		Assert.assertNotNull(expenses);
		Assert.assertEquals(1, expenses.size());
	}

	@Test
	public void shouldRetunTheSumOfExpensesFromYearAndMonth() {

		@SuppressWarnings("unused")
		Expense testExpense = testEntityManager
				.persist(Expense.newExpense("Rent", new BigDecimal(1500), LocalDate.of(2022, 9, 1), Category.HOUSING));

		@SuppressWarnings("unused")
		Expense testExpense2 = testEntityManager
				.persist(Expense.newExpense("Hydro", new BigDecimal(100), LocalDate.of(2022, 8, 15), Category.HOUSING));

		@SuppressWarnings("unused")
		Expense testExpense3 = testEntityManager
				.persist(Expense.newExpense("Grocery", new BigDecimal(50), LocalDate.of(2022, 8, 10), Category.FOOD));

		BigDecimal sum = expenseRepository.sumValuesByYearAndMonth(2022, 8);
		Assert.assertNotNull(sum);
		Assert.assertEquals(150, 150);
	}

	@Test
	public void shouldRetunTheSumOfCategory() {

		@SuppressWarnings("unused")
		Expense testExpense = testEntityManager
				.persist(Expense.newExpense("Rent", new BigDecimal(1500), LocalDate.of(2022, 8, 1), Category.HOUSING));

		@SuppressWarnings("unused")
		Expense testExpense2 = testEntityManager
				.persist(Expense.newExpense("Hydro", new BigDecimal(100), LocalDate.of(2022, 8, 15), Category.HOUSING));

		@SuppressWarnings("unused")
		Expense testExpense3 = testEntityManager
				.persist(Expense.newExpense("Grocery", new BigDecimal(50), LocalDate.of(2022, 8, 10), Category.FOOD));

		List<ExpenseByCategoryDto> sum = expenseRepository.sumValueByCategory(2022, 8);
		Assert.assertNotNull(sum);

	}

}
