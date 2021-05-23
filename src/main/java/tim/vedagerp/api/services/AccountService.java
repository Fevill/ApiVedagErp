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

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.repositories.AccountRepository;


@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository; 

	// Le journal
    public Page<Account>  listSortOrder(String sort, String order, int page, int size) {
    	Pageable pageable=null;
    	if(order.equals("asc")) {
    		pageable=PageRequest.of(page, size, Sort.by(sort).ascending());
    	}else {
    		pageable=PageRequest.of(page, size, Sort.by(sort).descending());
    	}
		return accountRepository.findAll(pageable); 
        
    }
    
 // Le journal
    public List<Account>  list() {

		return accountRepository.findAll(); 
        
    }
	
	// Ajouter une écriture comptable
    public Account get(long id) throws NoSuchElementException{
    	return accountRepository.findById(id).get();
    }
	
    // Ajouter une écriture comptable
    public Account add(Account body) {
    	return accountRepository.save(body);
    }
    
    // Ajouter une écriture comptable
    public List<Account> addAll(List<Account> body) {
    	return accountRepository.saveAll(body);
    }
	
    // Modifier une écriture comptable
    public Account update(Account body) {
    	return accountRepository.save(body);
    }
	
	// Supprimer une écriture comptable
    public String delete(long id) throws EmptyResultDataAccessException{
    	accountRepository.deleteById(id);
    	return "Success";
    }
	
}
