package br.com.challenge.household_financial.income;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = IncomeController.class)
@AutoConfigureMockMvc
public class IncomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	IncomeRepository incomeRepository;
	
	@Test
	void shouldAddNewIncome() throws Exception{
		var income = new Income();
		income.setId(1L);
		income.setDescription("bonus");
		income.setValue(new BigDecimal(5000));
		income.setDate(LocalDate.of(2022, 9, 1));
		
		var dto = new IncomeRequestDto();
		dto.setId(1l);
		dto.setDescription("bonus");
		dto.setValue(new BigDecimal(5000));
		dto.setDate(LocalDate.of(2022, 9, 1));
				
		String requestJson = objectMapper.writeValueAsString(dto);
		
		Mockito.when(incomeRepository.save(Mockito.any())).thenReturn(income);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/income")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.header().stringValues("Location", "http://localhost/topics/1"))
		.andReturn();
				
	}
	
	@Test
	void shouldReturnAllTheIncome() throws Exception{
		var income = new Income();
		income.setDescription("bonus");
		income.setValue(new BigDecimal(5000));
		income.setDate(LocalDate.of(2022, 9, 1));

		var income2 = new Income();
		income2.setDescription("salario");
		income2.setValue(new BigDecimal(8000));
		income2.setDate(LocalDate.of(2022, 9, 10));

		Mockito.when(incomeRepository.incomeByDescription(Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(0L);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/income")
				.contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void shouldReturnRequestForDuplicateIncome() throws Exception{
		var income = new Income();
		income.setDescription("bonus");
		income.setValue(new BigDecimal(5000));
		income.setDate(LocalDate.of(2022, 9, 1));

		var income2 = new Income();
		income2.setDescription("bonus");
		income2.setValue(new BigDecimal(8000));
		income2.setDate(LocalDate.of(2022, 9, 1));
		
		Mockito.when(incomeRepository.incomeByDescription(Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(0L);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/income")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	}

	@Test
	void shouldReturnOkForDetailingExistingIncome() throws Exception {
		var income = new Income();
		income.setDescription("bonus");
		income.setValue(new BigDecimal(5000));
		income.setDate(LocalDate.of(2022, 9, 1));

		String json = "[{\"description\":\"bonus\",\"value\":5000,\"date\":\"2022-09-01\"}]";

		Mockito.when(incomeRepository.findByDescription("bonus")).thenReturn(List.of(income));
		mockMvc.perform(MockMvcRequestBuilders
				.get("/income").content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	}
	@Test
	void shouldReturnOkForSearchByYearAndMonth() throws Exception {
		var income = new Income();
		income.setDescription("bonus");
		income.setValue(new BigDecimal(5000));
		income.setDate(LocalDate.of(2022, 9, 1));

		String json = "[{\"description\":\"bonus\",\"value\":5000,\"date\":\"2022-09-01\"}]";

		Mockito.when(incomeRepository.findByYearAndMonth(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(List.of(income));
		mockMvc.perform(MockMvcRequestBuilders
				.get("http://localhost:8080/income/2022/9")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();

	}
	
	@Test
	void shouldReturnOkWhenDeletingExistingIncome() throws Exception{
		var income = new Income();
		income.setDescription("bonus");
		income.setValue(new BigDecimal(5000));
		income.setDate(LocalDate.of(2022, 9, 1));
		
		Mockito.when(incomeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(income));
		mockMvc.perform(MockMvcRequestBuilders
				.delete("http://localhost:8080/income/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		verify(incomeRepository, times(1)).deleteById(Mockito.anyLong());
	}

	
}
