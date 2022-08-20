package br.com.challenge.household_financial.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.challenge.household_financial.controller.dto.ExpenseRequestDto;
import br.com.challenge.household_financial.controller.dto.ExpenseResponseDto;
import br.com.challenge.household_financial.modelo.Expense;
import br.com.challenge.household_financial.repository.ExpenseRepository;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseRepository expenseRepository;

	@GetMapping
	public ResponseEntity<List<ExpenseResponseDto>> callAll() {
		List<ExpenseResponseDto> list = expenseRepository.findAll().stream().map(e -> new ExpenseResponseDto(e))
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExpenseResponseDto> detail(@PathVariable Long id) {
		Optional<Expense> expenseOptional = expenseRepository.findById(id);
		if (expenseOptional.isPresent()) {
			return ResponseEntity.ok(new ExpenseResponseDto(expenseOptional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<String> enroll(@RequestBody @Valid ExpenseRequestDto request,
			UriComponentsBuilder uriBuilder) {
		Expense entity = request.update(new Expense());

		if (alreadyExistExpenseWithSameDescription(entity)) {
			return ResponseEntity.badRequest().body("This expense already exist with the same description and date");
		}
		Expense expense = expenseRepository.save(entity);
		URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(expense.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid ExpenseRequestDto request) {

		Optional<Expense> expenseOptional = expenseRepository.findById(id);
		if (expenseOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Expense expense = expenseOptional.get();
		request.update(expense);
		expenseRepository.save(expense);
		return ResponseEntity.ok("Expense Updated");
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> delete(@PathVariable Long id) {

		Optional<Expense> expenseOptional = expenseRepository.findById(id);
		if (expenseOptional.isPresent()) {
			expenseRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();

	}

	private boolean alreadyExistExpenseWithSameDescription(Expense expense) {
		LocalDate date = expense.getDate();
		return expenseRepository.expenseByDescription(expense.getDescription(), date.getMonthValue(),
				date.getYear()) > 0;
	}
}