package tim.vedagerp.api.controller;

import java.util.ArrayList;
import java.util.Date;
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

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.model.AccountSolde;
import tim.vedagerp.api.model.Bilan;
import tim.vedagerp.api.model.BilanDetail;
import tim.vedagerp.api.model.Ibalance;
import tim.vedagerp.api.model.IBudgetRow;
import tim.vedagerp.api.model.Ledger;
import tim.vedagerp.api.model.Message;
import tim.vedagerp.api.model.ResultatNsRow;
import tim.vedagerp.api.model.ResultatRow;
import tim.vedagerp.api.model.BudgetRow;
import tim.vedagerp.api.model.Budget;
import tim.vedagerp.api.model.BudgetPart;
import tim.vedagerp.api.services.AccountService;
import tim.vedagerp.api.services.JournalService;
import tim.vedagerp.api.services.JournalPrevService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/journal")
public class JournalRowController {

	@Autowired
	JournalService journalService;

	@Autowired
	JournalPrevService journalPrevService;

	@Autowired
	AccountService accountService;

	private static Logger logger = LogManager.getLogger(JournalRowController.class);

	@GetMapping()
	public ResponseEntity<?> getJournal(@RequestParam("sort") String sort, @RequestParam("order") String order,
			@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("fyId") Long fyId,
			@RequestParam("nsId") Long nsId) {
		// Page<JournalRow> accounts =
		// journalService.listSortOrder(sort,order,page,size,fy,id);

		Page<JournalRow> accounts = journalService.listSortOrder(sort, order, page, size, fyId, nsId);
		return new ResponseEntity<>(accounts, HttpStatus.OK);

	}

	@GetMapping("/month")
	public ResponseEntity<?> getJournalByMonth(@RequestParam("month") int month, @RequestParam("fyId") Long fyId,
			@RequestParam("nsId") Long nsId) {
		// Page<JournalRow> accounts =
		// journalService.listSortOrder(sort,order,page,size,fy,id);

		List<JournalRow> accounts = journalService.listByMonth(month, fyId, nsId);
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
	public ResponseEntity<?> getJournal( @RequestParam("nsId") Long nsId,@RequestParam("fyId") Long fyId,@RequestParam("subAccountId") Long subAccountId, @RequestParam("month") int month) {
		try {
			List<JournalRow> ledger = journalService.getLedgerByNsAndFy(nsId, fyId, subAccountId, month);
			return new ResponseEntity<>(ledger, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Pas de valeur pour id: " + nsId, HttpStatus.OK);
		}
	}

	@GetMapping("/ledger/solde")
	public ResponseEntity<?> getLedgerSolde(@RequestParam("subAccountId") Long subAccountId,
			@RequestParam("fyId") Long fyId, @RequestParam("nsId") Long nsId) {
		try {
			Ledger ledger = new Ledger();
;
			ledger.setSolde(journalService.getSoldeByNsidFyid(nsId, fyId, subAccountId));
			return new ResponseEntity<>(ledger, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Pas de valeur pour id: " + nsId, HttpStatus.OK);
		}
	}

	@GetMapping("/banq-account/solde")
	public ResponseEntity<?> getAccountsSolde(@RequestParam("fyId") Long fyId, @RequestParam("nsId") Long nsId) {
		try {
			List<AccountSolde> accountSoldes = new ArrayList<>();
			accountSoldes = journalService.banqAccountSold(nsId, fyId);
			return new ResponseEntity<>(accountSoldes, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Pas de valeur pour id: " + nsId, HttpStatus.OK);
		}
	}

	@GetMapping("/balance")
	public ResponseEntity<?> getBalance(@RequestParam("nsId") Long nsId,@RequestParam("fyId") Long fyId ) {
		try {
			List<Ibalance> balance = journalService.getBalance(nsId,fyId);
			return new ResponseEntity<>(balance, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/accounts")
	public ResponseEntity<?> getAccount(@RequestParam("nsId") Long nsId,@RequestParam("fyId") Long fyId,@RequestParam("accountId") String accountId) {
		try {

			List<Account> accounts =  accountService.listSubLabelBilanStartWith(nsId,accountId);
			List<ResultatRow> resultats = new ArrayList<>();
			//
			for (Account account : accounts) {
				ResultatRow row = new ResultatRow();
				row.setAccount(account);
				row.setSolde(journalService.getSoldeByNsidFyid(nsId, fyId, account.getId()));
				row.setSoldeprev(journalPrevService.getSoldeByNsidFyid(nsId, fyId, account.getId()));
				resultats.add(row);

				logger.info(row);
			}
			return new ResponseEntity<>(resultats, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/budget-v1")
	public ResponseEntity<?> getBudget(
		@RequestParam("nsId") Long nsId,
		@RequestParam("start") String startstr,
		@RequestParam("end") String endstr,
		@RequestParam("accountId") String accountId) {
		try {

			List<Account> accounts =  accountService.listSubLabelBilanStartWith(nsId,accountId);
			List<BudgetRow> resultats = new ArrayList<>();
			//
			for (Account account : accounts) {
				BudgetRow row = new BudgetRow();
				row.setAccount(account);
				row.setSolde(journalService.getSoldeByNsidMonth(nsId, startstr,endstr, account.getId()));
				row.setSoldeprev(journalPrevService.getSoldeByNsidMonth(nsId, startstr,endstr, account.getId()));
				row.calculSoldeDiff();
				resultats.add(row);
			}
			return new ResponseEntity<>(resultats, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/budget")
	public ResponseEntity<?> getBudgetV2(
		@RequestParam("nsId") Long nsId,
		@RequestParam("fyId") Long fyId,
		@RequestParam("start") String start,
		@RequestParam("end") String end) {
		try {

			//List<Account> accounts =  accountService.listSubLabelBilanStartWith(nsId,accountId);
			Budget budget = new Budget();
			List<IBudgetRow> p =journalService.getBudgetProduitsList(nsId, fyId, start, end);
			List<IBudgetRow> c =journalService.getBudgetChargesList(nsId, fyId, start, end);
			BudgetPart revenus = new BudgetPart();
			BudgetPart spend = new BudgetPart();

			revenus.setRow(p);
			spend.setRow(c);

			budget.setRevenus(revenus);
			budget.setSpend(spend);
			budget.calculSolde();
			
			return new ResponseEntity<>(budget , HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/resultat")
	public ResponseEntity<?> getResultat(@RequestParam("nsId") Long nsId,@RequestParam("fyId") Long fyId) {
		try {
			ResultatRow resultat = journalService.getResultat(nsId, fyId);
			return new ResponseEntity<>(resultat, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/resultat/months")
	public ResponseEntity<?> getResultatByMonths(@RequestParam("nsId") Long nsId) {
		try {
			List<ResultatNsRow> resultats = journalService.getResultatByNs(nsId);
			return new ResponseEntity<>(resultats, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}


	@GetMapping("/bilan")
	/*
	 * prime == Actif ou Passif
	 */
	public ResponseEntity<?> getBilan(@RequestParam("nsId") Long nsId,@RequestParam("fyId") Long fyId) {
		try {
			Bilan bilan = journalService.getBilan(nsId, fyId);
			return new ResponseEntity<>(bilan, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/bilan-detail/passif")
	public ResponseEntity<?> getBilanDetailPassif(@RequestParam("nsId") Long nsId,@RequestParam("fyId") Long fyId) {
		try {
			List<BilanDetail> bilanDetail = journalService.getBilanPassifDetail(nsId, fyId);
			return new ResponseEntity<>(bilanDetail, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>("Erreur: " + ex.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/bilan-detail/actif")
	public ResponseEntity<?> getBilanDetailActif(@RequestParam("nsId") Long nsId,@RequestParam("fyId") Long fyId) {
		try {
			List<BilanDetail> bilanDetail = journalService.getBilanActifDetail(nsId, fyId);
			return new ResponseEntity<>(bilanDetail, HttpStatus.OK);
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
	public ResponseEntity<?> postJournalAll(@RequestBody List<JournalRow> body) {
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
