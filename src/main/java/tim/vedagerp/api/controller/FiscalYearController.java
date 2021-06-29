package tim.vedagerp.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tim.vedagerp.api.entities.FiscalYear;
import tim.vedagerp.api.model.Message;
import tim.vedagerp.api.services.FiscalYearService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/fiscalyear")
public class FiscalYearController {

	@Autowired
	FiscalYearService fisalyearService;
	
	private static Logger logger = LogManager.getLogger(FiscalYearController.class);

	@GetMapping()
	public ResponseEntity<?> getFiscalYear(
		@RequestParam("sort") String sort,
		@RequestParam("order") String order,
		@RequestParam("page") int page,
		@RequestParam("size") int size,
		@RequestParam("id") Long id,
		@RequestParam("query") String query) {
		logger.info("getFiscalYear");
		Page<FiscalYear> fiscalYear = fisalyearService.listSortOrder(sort,order,page,size,id,query);
		return new ResponseEntity<>(fiscalYear, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getFiscalYearAll() {
		logger.info("getFiscalYear");
		List<FiscalYear> fiscalYear = fisalyearService.list();
		return new ResponseEntity<>(fiscalYear, HttpStatus.OK);
	}
	
	@GetMapping("/all/{id}")
	public ResponseEntity<?> getFiscalYearAll(@PathVariable("id") long id) {
		logger.info("getFiscalYear");
		List<FiscalYear> fiscalYear = fisalyearService.listByNamespaceId(id);
		return new ResponseEntity<>(fiscalYear, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getFiscalYear(@PathVariable("id") long id) {
		logger.info("getFiscalYear");
		try {
			FiscalYear fiscalYear = fisalyearService.get(id);
			return new ResponseEntity<>(fiscalYear, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>(String.format("Pas de valeur pour id: %d", id), HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<?> postFiscalYear(@RequestBody FiscalYear body) {
		logger.info("postFiscalYear");
		try {
			FiscalYear fiscalYear = fisalyearService.add(body);
			return new ResponseEntity<>(fiscalYear, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}
	
	@PostMapping("/all")
	public ResponseEntity<?> postFiscalYearAll(@RequestBody List<FiscalYear> body) {
		logger.info("postFiscalYearAll");
		try {
			List<FiscalYear> fiscalYear = fisalyearService.addAll(body);
			return new ResponseEntity<>(fiscalYear, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@PutMapping()
	public ResponseEntity<?> putFiscalYear(@RequestBody FiscalYear body) {
		logger.info("putFiscalYear");
		try {
			FiscalYear fiscalYear = fisalyearService.update(body);
			return new ResponseEntity<>(fiscalYear, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delFiscalYear(@PathVariable("id") long id) {
		logger.info("delFiscalYear");

		try {
			Message res = new Message();
			res.setText(fisalyearService.delete(id));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<>(String.format("Id %d n'existe pas.", id), HttpStatus.OK);
		}

	}

}
