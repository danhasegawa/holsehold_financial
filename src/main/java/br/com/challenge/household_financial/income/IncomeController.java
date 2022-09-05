package br.com.challenge.household_financial.income;

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
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	private IncomeRepository incomeRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> enroll(@RequestBody @Valid IncomeRequestDto request,
			UriComponentsBuilder uriBuilder) {
		Income entity = request.update(new Income());

		if (alreadyExistIncomeWithSameDescription(entity)) {
			return ResponseEntity.badRequest().body("This income already exist with the same description and date");
		}
		Income income = incomeRepository.save(entity);
		URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(income.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@GetMapping
	public ResponseEntity<List<IncomeResponseDto>> incomeDetail(@RequestParam(required = false) String description) {

		List<IncomeResponseDto> list = (Strings.isBlank(description) ? incomeRepository.findAll()
				: incomeRepository.findByDescription(description)).stream().map(i -> new IncomeResponseDto(i))
				.collect(Collectors.toList());

		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<IncomeResponseDto> incomeDetail(@PathVariable Long id) {
		Optional<Income> incomeOptional = incomeRepository.findById(id);
		if (incomeOptional.isPresent()) {
			return ResponseEntity.ok(new IncomeResponseDto(incomeOptional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{year}/{month}")
	public ResponseEntity<List<IncomeResponseDto>> incomeYearAndMonth(@PathVariable Integer year,
			@PathVariable Integer month) {
		if (year == null || month == null) {
			return ResponseEntity.badRequest().build();
		} else if (year < 1 || month > 13) {
			return ResponseEntity.badRequest().build();
		} else {
			List<IncomeResponseDto> list = incomeRepository.findByYearAndMonth(year, month).stream()
					.map(i -> new IncomeResponseDto(i)).collect(Collectors.toList());
			return ResponseEntity.ok(list);
		}

	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid IncomeRequestDto request) {

		Optional<Income> incomeOptional = incomeRepository.findById(id);
		if (incomeOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Income income = incomeOptional.get();
		request.update(income);
		incomeRepository.save(income);
		return ResponseEntity.ok("Income updated");

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> delete(@PathVariable Long id) {

		Optional<Income> incomeOptional = incomeRepository.findById(id);
		if (incomeOptional.isPresent()) {
			incomeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	private boolean alreadyExistIncomeWithSameDescription(Income income) {
		LocalDate date = income.getDate();
		return incomeRepository.incomeByDescription(income.getDescription(), date.getMonthValue(), date.getYear()) > 0;
	}

}