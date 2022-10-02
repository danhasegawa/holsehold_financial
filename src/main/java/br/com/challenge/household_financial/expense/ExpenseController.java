package br.com.challenge.household_financial.expense;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseRepository expenseRepository;

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

	@GetMapping
	public ResponseEntity<List<ExpenseResponseDto>> expenseDetail(@RequestParam(required = false) String description) {
		List<ExpenseResponseDto> list = (Strings.isBlank(description) ? expenseRepository.findAll()
				: expenseRepository.findByDescription(description)).stream().map(i -> new ExpenseResponseDto(i))
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

	@GetMapping("/{year}/{month}")
	public ResponseEntity<List<ExpenseResponseDto>> expenseYearAndMonth(@PathVariable Integer year,
			@PathVariable Integer month) {
		if (year == null || month == null) {
			return ResponseEntity.badRequest().build();
		} else if (year < 1 || month > 12) {
			return ResponseEntity.badRequest().build();
		} else {
			List<ExpenseResponseDto> list = expenseRepository.findByYearAndMonth(year, month).stream()
					.map(e -> new ExpenseResponseDto(e)).collect(Collectors.toList());
			return ResponseEntity.ok(list);
		}
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
	public ResponseEntity<?> delete(@PathVariable Long id) {

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