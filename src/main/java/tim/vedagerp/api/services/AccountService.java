package tim.vedagerp.api.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.Category;
import tim.vedagerp.api.repositories.AccountRepository;
import tim.vedagerp.api.repositories.CategoryRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	private static Logger logger = LogManager.getLogger(AccountService.class);

	// Liste des comptes
	public Page<Account> listSortOrder(String sort, String order, int page, int size,Long id,String query,String option) {
		Pageable pageable = null;
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}
		
		logger.info(option);
		
		if(option.equals("sub")) {
			return accountRepository.findByQueryNamespaceIdAccountIsNotNull(id,query,pageable);
		}else {
			return accountRepository.findByQueryNamespaceIdAccountIsNull(id,query,pageable);
		}	

	}

	// Liste des comptes selon id de l'espace de travail
	public List<Account> listByNamespaceId(long id,String option) {
		if(option.equals("sub")) {
			return accountRepository. findAllByNamespaceIdAndAccountIsNotNullOrderByNumber(id);
		}else {
			return accountRepository. findAllByNamespaceIdAndAccountIsNullOrderByNumber(id);
		}	
	}
	
	// Le journal
	public List<Account> list() {
		return accountRepository.findAll();
	}

	// Ajouter une écriture comptable
	public Account get(long id) throws NoSuchElementException {
		return accountRepository.findById(id).get();
	}

	// Ajouter une écriture comptable
	public Account add(Account body) {
		logger.info(body);
		return accountRepository.save(body);
	}

	// Ajouter une écriture comptable
	public List<Account> addAll(List<Account> body) {
		
		List<Category> categories = this.delDoublon(body);
		List<Account> res = new ArrayList<Account>();
		for(Account acc : body) {
			for(Category c : categories) {
				if(acc.getCategory().getPrime().equals(c.getPrime()) && 
						acc.getCategory().getSecond().equals(c.getSecond())) {
					acc.setCategory(c);
				}
			}
		}
		try {
			res = accountRepository.saveAll(body);
		}catch(Exception ex) {
			logger.error("********************************************"+ex.getMessage());
		}
		return res;
		//return new ArrayList<Account>();
	}

	// Modifier une écriture comptable
	public Account update(Account body) {
		return accountRepository.save(body);
	}

	// Supprimer une écriture comptable
	public String delete(long id) throws EmptyResultDataAccessException {
		accountRepository.deleteById(id);
		return "Success";
	}

	// Supprimer les doublons des catégories et les sauvegarder dans la bases
	private List<Category> delDoublon(List<Account> body) {

		Set<Category> set = new HashSet<Category>();
		List<Category> categoriesInit = new ArrayList<Category>();
		List<Category> categoriesFinal = new ArrayList<Category>();
		Boolean catExist;

		for (Account acc : body) {
			categoriesInit.add(acc.getCategory());
		}
		
		for (Category cat : categoriesInit) {
			catExist = false;
			for (Category catu : categoriesFinal) {
				if(cat.getPrime().equals(catu.getPrime())&&cat.getSecond().equals(catu.getSecond())) {
					catExist=true;
					break;
				}else {
					catExist = false;
				}
			}
			if(!catExist) {
				categoriesFinal.add(cat);
			}
		}
		try {
			categoriesFinal = categoryRepository.saveAll(categoriesFinal);
		}catch(Exception ex) {
			logger.error("Error : "+ex.getMessage());
		}
		
		categoriesFinal = categoryRepository.findAll();
		return categoriesFinal;

	}

	/**
	 * Récupération de tous les comptes de réferences 
	 * @param id
	 * @return
	 */
    public List<Account> listRef(long id) {
		return accountRepository.findAllByNamespaceIdAndAccountIsNullOrderByNumber(id);
    }

	/**
	 * Récuperation de tous les sous comptes
	 * @param id
	 * @return
	 */
	public List<Account> listSub(long id) {
		return accountRepository.findAllByNamespaceIdAndAccountIsNotNullOrderByNumber(id);
    }


	/**
	 * Récuperation de tous les sous comptes débutant par
	 * @param id
	 * @return
	 */
	public List<Account> listSubStartWith(long id,String number) {
		return accountRepository.findAllByNamespaceIdAndNumberStartsWithAndAccountIsNotNullOrderByNumber(id,number);
    }

	/**
	 * Récuperation de tous les sous comptes en fonction du label bilan
	 * @param id
	 * @return
	 */
	public List<Account> listSubLabelBilanStartWith(long id,String label) {
		return accountRepository.findAllByNamespaceIdAndLabelBilanStartsWithAndAccountIsNotNullOrderByNumber(id,label);
    }



}
