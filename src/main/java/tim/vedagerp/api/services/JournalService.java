package tim.vedagerp.api.services;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
import tim.vedagerp.api.repositories.JournalRowRepository;

@Service
public class JournalService {

	@Autowired
	JournalRowRepository journalRowRepository;

	// Le journal
	public Page<JournalRow> listSortOrder(String sort, String order, int page, int size,String fy, Long id) {
		Pageable pageable = null;
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}
		Date start = parseDate(fy+"-01-01");
		Date end = parseDate(fy+"-12-31");
		return journalRowRepository.findAllByNamespaceIdAndDateOperationBetween(pageable, id,start,end);
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
	public Ledger getLedger(Long id,String fy,Long nsId) {

		Ledger ledger = new Ledger();
		Date start = parseDate(fy+"-01-01");
		Date end = parseDate(fy+"-12-31");
		ledger.setCredit(journalRowRepository.findByCreditIdAndNamespaceIdAndDateOperationBetween(id,nsId,start,end));
		ledger.setDebit(journalRowRepository.findByDebitIdAndNamespaceIdAndDateOperationBetween(id,nsId,start,end));

		return ledger;
	}



	public List<Iresultat> getResultat(String prime,String fy,Long nsId) {
		Date start = parseDate(fy+"-01-01");
		Date end = parseDate(fy+"-12-31");
		if (prime.equals("Charges")) {
			return journalRowRepository.getResultatCharges(nsId,start,end);
		}
		return journalRowRepository.getResultatProduits(nsId,start,end);
	}

	public List<Ibilan> getBilan(String prime,String opt,String fy,Long nsId) {
		Date start = parseDate(fy+"-01-01");
		Date end = parseDate(fy+"-12-31");
		if(opt.equals("I")) {
		     end = parseDate(fy+"-01-01");
		}
		if (prime.equals("Actif")) {
			return journalRowRepository.getBilanActif(nsId,start,end);
		}
		return journalRowRepository.getBilanPassif(nsId,start,end);
	}
	
	 public static Date parseDate(String date) {
	     try {
	         return (Date) new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }

	 

	public List<Ibalance> getBalance(String fy, Long nsId) {
		Date start = parseDate(fy+"-01-01");
		Date end = parseDate(fy+"-12-31");
		return journalRowRepository.getBalance(nsId,start,end);
	}

}
