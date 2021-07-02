package tim.vedagerp.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.model.Ibalance;
import tim.vedagerp.api.model.Ibilan;
import tim.vedagerp.api.model.Iresultat;
import tim.vedagerp.api.model.Ledger;
import tim.vedagerp.api.repositories.FiscalYearRepository;
import tim.vedagerp.api.repositories.JournalRowRepository;

@Service
public class JournalService {

	@Autowired
	JournalRowRepository journalRowRepository;

	@Autowired
	FiscalYearRepository fiscalYearRepository;

	private static Logger logger = LogManager.getLogger(JournalService.class);

	// Le journal
	public Page<JournalRow> listSortOrder(String sort, String order, int page, int size, Long fyId, Long nsId) {
		Pageable pageable = null;
		logger.info("delAccount");
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();

		return journalRowRepository.findAllByNamespaceIdAndDateOperationBetween(pageable, nsId, start, end);
	}

	// Le journal
	public List<JournalRow> list() {

		return journalRowRepository.findAll();
	}

	// Le journal by namespace
	public List<JournalRow> listByNamespaceId(Long id) {

		return journalRowRepository.findAllByNamespaceId(id);
	}

	// Ajouter une écriture comptable
	public JournalRow get(long id) throws NoSuchElementException {
		return journalRowRepository.findById(id).get();
	}

	// Ajouter une écriture comptable
	public JournalRow add(JournalRow body) {
		return journalRowRepository.saveAndFlush(body);
	}

	// Modifier une écriture comptable
	public JournalRow update(JournalRow body) {
		return journalRowRepository.saveAndFlush(body);
	}

	// Supprimer une écriture comptable
	public String delete(long id) throws EmptyResultDataAccessException {
		journalRowRepository.deleteById(id);
		return "Success";
	}

	public List<JournalRow> addAll(List<JournalRow> body) {
		return journalRowRepository.saveAll(body);
	}

	// Récupération du grand livre
	public List<JournalRow> getLedgerByNsAndFy(Long nsId, Long fyId, Long accountid ,int month ) {
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();
		month=month+1;
		return journalRowRepository.getLedger(nsId, accountid, start, end, month);
	}

	public List<Iresultat> getResultat(String prime, String fy, Long nsId) {
		Date start = parseDate(fy + "-01-01");
		Date end = parseDate(fy + "-12-31");
		if (prime.equals("Charges")) {
			return journalRowRepository.getResultatCharges(nsId, start, end);
		}
		return journalRowRepository.getResultatProduits(nsId, start, end);
	}

	public List<Ibilan> getBilan(String prime, String opt, String fy, Long nsId) {
		Date start = parseDate(fy + "-01-01");
		Date end = parseDate(fy + "-12-31");
		if (opt.equals("I")) {
			end = parseDate(fy + "-01-01");
		}
		if (prime.equals("Actif")) {
			return journalRowRepository.getBilanActif(nsId, start, end);
		}
		return journalRowRepository.getBilanPassif(nsId, start, end);
	}

	public static Date parseDate(String date) {
		try {
			return (Date) new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public List<Ibalance> getBalance(String fy, Long nsId) {
		Date start = parseDate(fy + "-01-01");
		Date end = parseDate(fy + "-12-31");
		return journalRowRepository.getBalance(nsId, start, end);
	}

	public List<JournalRow> listByMonth(int month, Long fyId, Long nsId) {

		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();
		month = month + 1;
		return journalRowRepository.getJournalByNsidFyidMonth(nsId, start, end, month);
	}

	/**
	 * Solde d'un sous comptes comptable
	 * 
	 */
	public float getSoldeByNsidFyid(Long nsId,Long fyId, Long subAccountId) {
		float solde = 0;
		float soldeCredit = 0;
		float soldeDebit = 0;
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();
		soldeCredit = journalRowRepository.getSoldeCreditByNsidFyid(nsId, subAccountId, start, end);
		soldeDebit = journalRowRepository.getSoldeDebitByNsidFyid(nsId, subAccountId, start, end);
		solde = soldeDebit - soldeCredit;
		solde = this.roundFloat(solde, 2);
		return solde;
	}

	
    private float roundFloat(float f, int places) {
 
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

}
