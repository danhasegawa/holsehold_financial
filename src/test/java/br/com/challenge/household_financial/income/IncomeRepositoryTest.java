package br.com.challenge.household_financial.income;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

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
	public void shouldReturnIncomeByDescriptionNotNull() {
		
		testEntityManager.persist(Income.newIncome("Salary", new BigDecimal(5000), LocalDate.of(2022, 9, 10)));
		
		Long test = incomeRepository.incomeByDescription("salary", 9, 10);
		assertThat(test)
		.isNotNull()
		.isEqualTo(0);
		
	}

}
