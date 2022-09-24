package br.com.challenge.household_financial.expense;

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

import br.com.challenge.household_financial.category.Category;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc
class ExpenseControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	ExpenseRepository expenseRepository;
	
	@Test
	void shouldAddNewExpense() throws Exception {
		var expense = new Expense();
		expense.setId(1l);
		expense.setDescription("Oil Change");
		expense.setValue(new BigDecimal(250));
		expense.setDate(LocalDate.of(2022, 9, 10));
		expense.setCategory(Category.UNEXPECTED);
		
		var dto = new ExpenseRequestDto();
		dto.setId(1l);
		dto.setDescription("Oil Change");
		dto.setValue(new BigDecimal(250));
		dto.setDate(LocalDate.of(2022, 9, 10));
		dto.setCategory(Category.UNEXPECTED);
		
		String requestJson = objectMapper.writeValueAsString(dto);
		
		Mockito.when(expenseRepository.save(Mockito.any())).thenReturn(expense);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/expense")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.header().stringValues("Location", "http://localhost/topics/1"))
		.andReturn();
	}
	
	@Test
	void shouldReturnAllExpense() throws Exception{
		var expense = new Expense();
		expense.setId(1l);
		expense.setDescription("Oil Change");
		expense.setValue(new BigDecimal(250));
		expense.setDate(LocalDate.of(2022, 9, 10));
		expense.setCategory(Category.UNEXPECTED);
		
		var expense2 = new Expense();
		expense2.setId(1l);
		expense2.setDescription("Dinner");
		expense2.setValue(new BigDecimal(80));
		expense2.setDate(LocalDate.of(2022, 9, 11));
		expense2.setCategory(Category.FOOD);
		
		Mockito.when(expenseRepository.expenseByDescription(Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(0L);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/expense")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andReturn();
	}
	
	@Test
	void shouldReturnBadRequestForDuplicateExpenseWhitSameDescriptionAndDate() throws Exception{
		var expense = new Expense();
		expense.setId(1l);
		expense.setDescription("Oil Change");
		expense.setValue(new BigDecimal(250));
		expense.setDate(LocalDate.of(2022, 9, 10));
		expense.setCategory(Category.UNEXPECTED);
		
		var expense2 = new Expense();
		expense2.setId(1l);
		expense2.setDescription("Oil Change");
		expense2.setValue(new BigDecimal(50));
		expense2.setDate(LocalDate.of(2022, 9, 10));
		expense2.setCategory(Category.UNEXPECTED);
		
		Mockito.when(expenseRepository.expenseByDescription(Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(0L);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/expense")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void shouldReturnOkForDetailingExistingExpense() throws Exception{
		var expense = new Expense();
		expense.setId(1l);
		expense.setDescription("Oil Change");
		expense.setValue(new BigDecimal(250));
		expense.setDate(LocalDate.of(2022, 9, 10));
		expense.setCategory(Category.UNEXPECTED);
		
		Mockito.when(expenseRepository.findByDescription("Troca oleo")).thenReturn(List.of(expense));
		mockMvc.perform(MockMvcRequestBuilders
				.get("/expense")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void shouldReturnSearchByYearAndMonty() throws Exception{
		var expense = new Expense();
		expense.setId(1l);
		expense.setDescription("Oil Change");
		expense.setValue(new BigDecimal(250));
		expense.setDate(LocalDate.of(2022, 9, 10));
		expense.setCategory(Category.UNEXPECTED);
		
		Mockito.when(expenseRepository.findByYearAndMonth(Mockito.anyInt(), Mockito.anyInt())).thenReturn(List.of(expense));
		mockMvc.perform(MockMvcRequestBuilders
				.get("http://localhost:8080/expense/2022/9")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void shouldReturnOkWhenDeletingExistingExpense() throws Exception{
		var expense = new Expense();
		expense.setId(1l);
		expense.setDescription("Oil Change");
		expense.setValue(new BigDecimal(250));
		expense.setDate(LocalDate.of(2022, 9, 10));
		expense.setCategory(Category.UNEXPECTED);
		
		Mockito.when(expenseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expense));
		mockMvc.perform(MockMvcRequestBuilders
				.delete("http://localhost:8080/expense/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
		verify(expenseRepository, times(1)).deleteById(Mockito.anyLong());
	}
	
	
}
