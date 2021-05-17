package tim.vedagerp.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.services.AccountService;

@Controller
@RequestMapping("api/v1/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping()
	public ResponseEntity<?> getAccount() {
		List<Account> accounts = accountService.list();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAccount(@PathVariable("id") long id) {
		try {
			Account account = accountService.get(id);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Pas de valeur pour id: " + id, HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<?> postAccount(@RequestBody Account body) {
		try {
			Account account = accountService.add(body);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@PutMapping()
	public ResponseEntity<?> putAccount(@RequestBody Account body) {
		try {
			Account account = accountService.update(body);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delAccount(@PathVariable("id") long id) {

		try {
			String res = accountService.delete(id);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<>(String.format("Id %d n'existe pas.", id), HttpStatus.OK);
		}

	}

}
