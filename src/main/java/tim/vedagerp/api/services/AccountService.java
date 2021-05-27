package tim.vedagerp.api.services;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

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

	// Le journal
	public Page<Account> listSortOrder(String sort, String order, int page, int size,Long id) {
		Pageable pageable = null;
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}

		return accountRepository.findAllByNamespaceId(pageable,id);

	}

	// Le journal
	public List<Account> listByNamespaceId(long id) {
		return accountRepository.findAllByNamespaceId(id);
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
		return accountRepository.save(body);
	}

	// Ajouter une écriture comptable
	public List<Account> addAll(List<Account> body) {
		
		List<Category> categories = this.delDoublon(body);
		for(Account acc : body) {
			for(Category c : categories) {
				if(acc.getCategory().getPrime().equals(c.getPrime()) && 
						acc.getCategory().getSecond().equals(c.getSecond())) {
					acc.setCategory(c);
				}
			}
		}
		return accountRepository.saveAll(body);
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
		List<Category> categories = new ArrayList<Category>();

		for (Account acc : body) {
			set.add(acc.getCategory());
		}
		List<Category> unique = set.stream()
				.collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Category::getSecond, (s1, s2) -> {
					return s2.compareTo(s1);
				}))), ArrayList::new));
		
		
		try {
			categories = categoryRepository.saveAll(unique);
		}catch(Exception ex) {
			logger.error("Error : "+ex.getMessage());
		}
		
		categories = categoryRepository.findAll();
		return categories;

	}



}
