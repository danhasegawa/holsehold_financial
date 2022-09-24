package br.com.challenge.household_financial.income;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class IncomeRepositoryTest {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void shouldReturnIncomeByDescription() {

		testEntityManager.persist(Income.newIncome("Salary", new BigDecimal(5000), LocalDate.of(2022, 9, 10)));

		Long test = incomeRepository.incomeByDescription("Salary", 9, 2022);
		assertThat(test).isNotNull();

	}

	@Test
	public void shouldReturnIncomeByDescriptionNotEqualForIncomeNotOnDb() {

		List<Income> income = incomeRepository.findByDescription("Bonus");
		assertThat(income).isNotNull().isNotEqualTo(0);
	}

	@Test
	public void shouldReturnOkfindByDescription() {

		testEntityManager.persist(Income.newIncome("Return", new BigDecimal(200), LocalDate.of(2022, 9, 20)));

		List<Income> incomeByDescription = incomeRepository.findByDescription("Return");
		Assert.assertNotNull(incomeByDescription);
		Assert.assertEquals(1, incomeByDescription.size());
		;

	}

	@Test
	@SuppressWarnings("unused")
	public void shouldReturnOkFindByYearAndMonth() {
		Income september = testEntityManager
				.persist(Income.newIncome("Salary", new BigDecimal(8000), LocalDate.of(2022, 9, 5)));
		Income august = testEntityManager
				.persist(Income.newIncome("New Income", new BigDecimal(1000), LocalDate.of(2022, 7, 15)));

		List<Income> income = incomeRepository.findByYearAndMonth(2022, 9);
		Assert.assertNotNull(income);
		Assert.assertEquals(1, income.size());
	}

	@Test
	@SuppressWarnings("unused")
	public void shouldRetunTheSumOfIncomesFromYearAndMonth() {
		var income1 = testEntityManager
				.persist(Income.newIncome("Salary", new BigDecimal(8000), LocalDate.of(2022, 9, 5)));
		var income2 = testEntityManager
				.persist(Income.newIncome("BitCoin", new BigDecimal(10000), LocalDate.of(2022, 8, 15)));
		var income3 = testEntityManager
				.persist(Income.newIncome("Bonus", new BigDecimal(800), LocalDate.of(2022, 9, 14)));
		var income4 = testEntityManager
				.persist(Income.newIncome("New Income", new BigDecimal(1000), LocalDate.of(2022, 9, 15)));

		BigDecimal sum = incomeRepository.sumValuesByYearAndMonth(2022, 9);
		Assert.assertNotNull(sum);
		Assert.assertEquals(9800, 9800);

		BigDecimal sum2 = incomeRepository.sumValuesByYearAndMonth(2021, 9);
		Assert.assertNull(sum2);
	}
}