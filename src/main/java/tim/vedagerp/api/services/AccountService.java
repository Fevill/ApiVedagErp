package tim.vedagerp.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.repositories.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository; 

	// Le journal
    public List<Account> list() {
        return accountRepository.findAll();
    }
	
	// Ajouter une écriture comptable
    public Account get(long id) throws NoSuchElementException{
    	return accountRepository.findById(id).get();
    }
	
    // Ajouter une écriture comptable
    public Account add(Account body) {
    	return accountRepository.saveAndFlush(body);
    }
	
    // Modifier une écriture comptable
    public Account update(Account body) {
    	return accountRepository.saveAndFlush(body);
    }
	
	// Supprimer une écriture comptable
    public String delete(long id) throws EmptyResultDataAccessException{
    	accountRepository.deleteById(id);
    	return "Success";
    }
	
}
