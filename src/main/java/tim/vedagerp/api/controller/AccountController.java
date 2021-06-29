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



import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.model.Message;
import tim.vedagerp.api.services.AccountService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	private static Logger logger = LogManager.getLogger(AccountController.class);

	@GetMapping()
	public ResponseEntity<?> getAccount(
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("id") Long id,
			@RequestParam("query") String query,
			@RequestParam("option") String option) {
		logger.info("getAccount");
		Page<Account> accounts = accountService.listSortOrder(sort,order,page,size,id, query,option);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}
	

	@GetMapping("/all")
	public ResponseEntity<?> getAccountAll() {
		logger.info("getAccountAll");
		List<Account> accounts = accountService.list();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/all-ref")
	public ResponseEntity<?> getAccountAllRef(@RequestParam("nsId") Long id) {
		logger.info("getAccountAll - Ref");
		List<Account> accounts = accountService.listRef(id);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/all-sub")
	public ResponseEntity<?> getAccountAllSub(@RequestParam("nsId") Long id) {
		logger.info("getAccountAll - Sub");
		List<Account> accounts = accountService.listSub(id);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}
	
	
	@GetMapping("/all/{id}")
	public ResponseEntity<?> getAccountAll(@PathVariable("id") long id,@RequestParam("option") String option) {
		logger.info("getAccountAll");
		List<Account> accounts = accountService.listByNamespaceId(id,option);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAccount(@PathVariable("id") long id) {
		logger.info("getAccount");
		try {
			Account account = accountService.get(id);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>(String.format("Pas de valeur pour id: %d", id), HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<?> postAccount(@RequestBody Account body) {
		logger.info("postAccount ");
		try {
			Account account = accountService.add(body);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}
	
	@PostMapping("/all")
	public ResponseEntity<?> postAccountAll(@RequestBody List<Account> body) {
		logger.info("postAccountAll");
		try {
			List<Account> accounts = accountService.addAll(body);
			return new ResponseEntity<>(accounts, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@PutMapping()
	public ResponseEntity<?> putAccount(@RequestBody Account body) {
		logger.info("putAccount");
		try {
			Account account = accountService.update(body);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delAccount(@PathVariable("id") long id) {
		logger.info("delAccount");

		try {
			Message res = new Message();
			res.setText(accountService.delete(id));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<>(String.format("Id %d n'existe pas.", id), HttpStatus.OK);
		}

	}

}
