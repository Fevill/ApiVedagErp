package tim.vedagerp.api.services;

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
	public Page<JournalRow> listSortOrder(String sort, String order, int page, int size, Long id) {
		Pageable pageable = null;
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}
		return journalRowRepository.findAllByNamespaceId(pageable, id);
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
	public Ledger getLedger(Long id,Long nsId) {

		Ledger ledger = new Ledger();
		ledger.setCredit(journalRowRepository.findByCreditIdAndNamespaceId(id,nsId));
		ledger.setDebit(journalRowRepository.findByDebitIdAndNamespaceId(id,nsId));

		return ledger;
	}

	public List<Ibalance> getBalance() {

		return journalRowRepository.getBalance();
	}

	public List<Iresultat> getResultat(String prime) {
		if (prime.equals("Charges")) {
			return journalRowRepository.getResultatCharges();
		}
		return journalRowRepository.getResultatProduits();
	}

	public List<Ibilan> getBilan(String prime) {
		if (prime.equals("Actif")) {
			return journalRowRepository.getBilanActif();
		}
		return journalRowRepository.getBilanPassif();
	}

}
