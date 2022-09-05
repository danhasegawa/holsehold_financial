package br.com.challenge.household_financial.monthly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
public class MonthlySummaryController {
	
	@Autowired
	private MonthlySummaryService monthlySummaryService;
	
	@GetMapping("/{year}/{month}")
	public ResponseEntity<MonthlySummaryResponseDto> summary(@PathVariable Integer year, @PathVariable Integer month){
		return ResponseEntity.ok(monthlySummaryService.getSummary(year, month));
	}
	
	
	
}
