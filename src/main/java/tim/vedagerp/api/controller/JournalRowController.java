package tim.vedagerp.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

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

import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.model.Ibalance;
import tim.vedagerp.api.model.Ibilan;
import tim.vedagerp.api.model.Iresultat;
import tim.vedagerp.api.model.Ledger;
import tim.vedagerp.api.model.Message;
import tim.vedagerp.api.services.JournalService;


@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/journal")
public class JournalRowController {
	
	@Autowired
	JournalService journalService;

	@GetMapping()
	public ResponseEntity<?> getJournal(@RequestParam("sort") String sort,@RequestParam("order") String order,@RequestParam("page") int page,@RequestParam("size") int size) {
		Page<JournalRow> accounts = journalService.listSortOrder(sort,order,page,size);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getJournal() {
		List<JournalRow> accounts = journalService.list();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getJournal(@PathVariable("id") long id) {
		try {
			JournalRow account = journalService.get(id);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Pas de valeur pour id: " + id, HttpStatus.OK);
		}
	}
	
	@GetMapping("/ledger")
	public ResponseEntity<?> getJournal(@RequestParam("accountId") Long id) {
		try {
			Ledger ledger = journalService.getLedger(id);
			return new ResponseEntity<>(ledger, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Pas de valeur pour id: " + id, HttpStatus.OK);
		}
	}
	
	@GetMapping("/balance")
	public ResponseEntity<?> getBalance() {
		try {
			List<Ibalance> balance = journalService.getBalance();
			return new ResponseEntity<>(balance, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/resultat")
	public ResponseEntity<?> getResultat(@RequestParam("prime") String prime) {
		try {
			List<Iresultat> resultat = journalService.getResultat(prime);
			return new ResponseEntity<>(resultat, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/bilan")
	/*
	 * prime == Actif ou Passif
	 */
	public ResponseEntity<?> getBilan(@RequestParam("prime") String prime) {
		try {
			List<Ibilan> bilan = journalService.getBilan(prime);
			return new ResponseEntity<>(bilan, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<?> postJournal(@RequestBody JournalRow body) {
		try {
			JournalRow account = journalService.add(body);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}
	
	@PostMapping("/all")
	public ResponseEntity<?> postJournalAll(@RequestBody  List<JournalRow> body) {
		try {
			 List<JournalRow> account = journalService.addAll(body);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@PutMapping()
	public ResponseEntity<?> putJournal(@RequestBody JournalRow body) {
		try {
			JournalRow account = journalService.update(body);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delJournal(@PathVariable("id") long id) {

		try {
			Message res = new Message();
			res.setText(journalService.delete(id));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<>(String.format("Id %d n'existe pas.", id), HttpStatus.OK);
		}

	}


}
