package tim.vedagerp.api.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.repositories.AccountRepository;

@SpringBootTest
public class JournalRowControllerTest {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountController accountController;

	@SuppressWarnings("unchecked")
	@Test
    public void getAccountTest() throws Exception{
		
		ResponseEntity<?> response;
		List<Account> actualList;
		Long expectedLength = 5L;
		Long actualLength;
		Account expectedAccount1 = new Account ();
		expectedAccount1.setLabel("Label "+1);
		expectedAccount1.setNumber("Number "+1);
		
		// Ajouter des comptes
		for(Long i=1L;i<=expectedLength;i++) {
			Account account = new Account ();
			account.setLabel("Label "+i);
			account.setNumber("Number "+i);
			
			accountRepository.save(account);
		}
		
		// Début du test
		response = accountController.getAccount();
		actualList = (List<Account>) response.getBody();
		actualLength = (long) actualList.size();
		assertEquals(expectedLength, actualLength);
		assertEquals(expectedAccount1.getLabel(),actualList.get(0).getLabel());
		assertEquals(expectedAccount1.getNumber(),actualList.get(0).getNumber());

		
		// Supprimer les comptes crées
		for(Account account: actualList) {
			accountRepository.deleteById(account.getId());
		}
        
    }
	
	@SuppressWarnings("unchecked")
	@Test
    public void getAccountEmptyTest() throws Exception{
		
		ResponseEntity<?> response;
		List<Account> actualList;
		Long expectedLength = 0L;
		Long actualLength;

		// Début du test
		response = accountController.getAccount();
		actualList = (List<Account>) response.getBody();
		actualLength = (long) actualList.size();
		assertEquals(expectedLength, actualLength);
        
    }
	
	@Test
    public void getAccount() throws Exception{
		
		Long id;
		Account actualAccount;
		Account expectedAccount = new Account ();
		
		expectedAccount.setLabel("Label "+10);
		expectedAccount.setNumber("Number "+10);
		
		// Ajouter des comptes
		id = accountRepository.saveAndFlush(expectedAccount).getId();
				
		// Début du test
		actualAccount = (Account) accountController.getAccount(id).getBody();
		assertEquals(expectedAccount.getLabel(),actualAccount.getLabel());
		assertEquals(expectedAccount.getNumber(),actualAccount.getNumber());
		
		// Supprimer les comptes crées
		accountRepository.deleteById(actualAccount.getId());
		
    }
	
	@Test
    public void getAccountIdNotExist() throws Exception{
		
		Long id= 10L;
		String actual;
		String expected = "Pas de valeur pour id: "+id;
		
		// Début du test
		actual = (String) accountController.getAccount(id).getBody();
		assertEquals(expected,actual);
		
    }
	
	@Test
    public void getAccountIdNull() throws Exception{
		
		Long id= null;
		String actual;
		String expected = "Id est null";
		
		// Début du test
		actual = (String) accountController.getAccount(id).getBody();
		assertEquals(expected,actual);
		
    }
	
	@Test // Test de création d'un compte
    public void postAccount() throws Exception{
		
        final Long id = 15L;
		Account actualAccount;
		Account expectedAccount = new Account ();
		
		expectedAccount.setLabel("Label "+id);
		expectedAccount.setNumber("Number "+id);
		
		// Début du test
		actualAccount = (Account) accountController.postAccount(expectedAccount).getBody();
		assertEquals(expectedAccount.getLabel(),actualAccount.getLabel());
		assertEquals(expectedAccount.getNumber(),actualAccount.getNumber());
		
		// Supprimer les comptes crées
		accountRepository.deleteById(actualAccount.getId());
		
    }
	
	@Test // Modifier un compte
    public void putAccount() throws Exception{
		
        final Long id = 15L;
        Long actualid ;
		Account actualAccount;
		Account expectedAccount = new Account ();
		
		
		expectedAccount.setLabel("Label "+id);
		expectedAccount.setNumber("Number "+id);
		
		// Ajouter des comptes
		actualid = accountRepository.saveAndFlush(expectedAccount).getId();
		
		expectedAccount.setId(actualid);
		expectedAccount.setLabel("Label Modifier "+id);
		expectedAccount.setNumber("Number Modifier "+id);
		
		// Début du test
		actualAccount = (Account) accountController.putAccount(expectedAccount).getBody();
		assertEquals(expectedAccount.getLabel(),actualAccount.getLabel());
		assertEquals(expectedAccount.getNumber(),actualAccount.getNumber());
		
		// Supprimer les comptes crées
		accountRepository.deleteById(actualid);
		
    }
	
	@Test  // Supprimer un compte
    public void delAccount() throws Exception{
		
		 	final Long id = 20L;
		 	Long actualid ;
			String actual;
			String expected = "Success";
			Account account = new Account ();
			
			account.setLabel("Label "+id);
			account.setNumber("Number "+id);
			
			// Ajouter des comptes
			actualid = accountRepository.saveAndFlush(account).getId();
			
			// Début du test
			actual = (String) accountController.delAccount(actualid).getBody();
			assertEquals(expected,actual);

    }
	
}
