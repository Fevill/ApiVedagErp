package tim.vedagerp.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.repositories.AccountRepository;
import tim.vedagerp.api.repositories.JournalRowRepository;

@Service
public class JournalService {
	
	@Autowired
	JournalRowRepository journalRowRepository; 

	// Le journal
    public List<JournalRow> list() {
        return journalRowRepository.findAll();
    }
	
	// Ajouter une écriture comptable
    public JournalRow get(long id) throws NoSuchElementException{
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
    public String delete(long id) throws EmptyResultDataAccessException{
    	journalRowRepository.deleteById(id);
    	return "Success";
    }
	
}
