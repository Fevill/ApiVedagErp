package tim.vedagerp.api.controller;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.repositories.AccountRepository;
import tim.vedagerp.api.repositories.JournalRowRepository;

@SpringBootTest
public class JournalRowControllerTest {
	
	@Autowired
	JournalRowRepository journalRowRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	JournalRowController journalRowController;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
    public void getJournalRowTest() throws Exception{
		
		ResponseEntity<?> response;
		List<JournalRow> actualList;
		Long expectedLength = 5L;
		Long actualLength;
		JournalRow expectedJournalRow1 = new JournalRow ();
		
		Account debit = new Account ();
		debit.setLabel("Debit Label "+1);
		debit.setNumber("Debit Number "+1);
		debit=accountRepository.saveAndFlush(debit);
		
		
		Account credit = new Account ();
		credit.setLabel("Credit Label "+1);
		credit.setNumber("Credit Number "+1);
		credit=accountRepository.saveAndFlush(credit);
		
		expectedJournalRow1.setDateOperation(new Date(01,01,1990)); 
		expectedJournalRow1.setLabel("Label "+1);
		expectedJournalRow1.setDebit(debit);
		expectedJournalRow1.setCredit(credit);
		expectedJournalRow1.setAmount(1000);
		journalRowRepository.save(expectedJournalRow1);
		
		// Ajouter des comptes
		for(Long i=1L;i<=expectedLength;i++) {
			JournalRow journalRow = new JournalRow ();
			journalRow.setLabel("Label "+i);
			journalRow.setDebit(debit);
			journalRow.setCredit(credit);
			journalRow.setAmount(1000);
			
			journalRowRepository.save(journalRow);
		}
		
		// Début du test
		response = journalRowController.getJournal();
		actualList = (List<JournalRow>) response.getBody();
		actualLength = (long) actualList.size();
		assertEquals(expectedJournalRow1.getLabel(),actualList.get(0).getLabel());
		assertEquals(expectedJournalRow1.getAmount(),actualList.get(0).getAmount(),0f);
		assertEquals(expectedJournalRow1.getCredit().getLabel(),actualList.get(0).getCredit().getLabel());
		assertEquals(expectedJournalRow1.getDebit().getLabel(),actualList.get(0).getDebit().getLabel());

		
		// Supprimer les comptes crées
		for(JournalRow journalRow: actualList) {
			journalRowRepository.deleteById(journalRow.getId());
		}
        
    }
	
	
	@SuppressWarnings("unchecked")
	@Test
    public void getJournalRowEmptyTest() throws Exception{
		
		ResponseEntity<?> response;
		List<JournalRow> actualList;
		Long expectedLength = 0L;
		Long actualLength;

		// Début du test
		response = journalRowController.getJournal();
		actualList = (List<JournalRow>) response.getBody();
		actualLength = (long) actualList.size();
		assertEquals(expectedLength, actualLength);
        
    }
	
	@Test
    public void getJournalRow() throws Exception{
		
		Long id;
		JournalRow actualJournalRow;
		JournalRow expectedJournalRow = new JournalRow ();
		
		Account debit = new Account ();
		debit.setLabel("Debit Label "+1);
		debit.setNumber("Debit Number "+1);
		debit=accountRepository.saveAndFlush(debit);
		
		
		Account credit = new Account ();
		credit.setLabel("Credit Label "+1);
		credit.setNumber("Credit Number "+1);
		credit=accountRepository.saveAndFlush(credit);
		
		expectedJournalRow.setDateOperation(new Date(01,01,1990)); 
		expectedJournalRow.setLabel("Label "+1);
		expectedJournalRow.setDebit(debit);
		expectedJournalRow.setCredit(credit);
		expectedJournalRow.setAmount(1000);
		
		
		// Ajouter des comptes
		id = journalRowRepository.saveAndFlush(expectedJournalRow).getId();
				
		// Début du test
		actualJournalRow = (JournalRow) journalRowController.getJournal(id).getBody();
		assertEquals(expectedJournalRow.getLabel(),actualJournalRow.getLabel());
		assertEquals(expectedJournalRow.getAmount(),actualJournalRow.getAmount(),0f);
		assertEquals(expectedJournalRow.getCredit().getLabel(),actualJournalRow.getCredit().getLabel());
		assertEquals(expectedJournalRow.getDebit().getLabel(),actualJournalRow.getDebit().getLabel());
		
		// Supprimer les comptes crées
		journalRowRepository.deleteById(actualJournalRow.getId());
		
    }
	
	@Test
    public void getJournalRowIdNotExist() throws Exception{
		
		Long id= 10L;
		String actual;
		String expected = "Pas de valeur pour id: "+id;
		
		// Début du test
		actual = (String) journalRowController.getJournal(id).getBody();
		assertEquals(expected,actual);
		
    }
	
	@Test // Test de création d'une écriture comptable
    public void postJournalRow() throws Exception{
		
        final Long id = 15L;
		JournalRow actualJournalRow;
		JournalRow expectedJournalRow = new JournalRow ();
		
		Account debit = new Account ();
		debit.setLabel("Debit Label "+1);
		debit.setNumber("Debit Number "+1);
		debit=accountRepository.saveAndFlush(debit);
		
		
		Account credit = new Account ();
		credit.setLabel("Credit Label "+1);
		credit.setNumber("Credit Number "+1);
		credit=accountRepository.saveAndFlush(credit);
		
		expectedJournalRow.setDateOperation(new Date(01,01,1990)); 
		expectedJournalRow.setLabel("Label "+1);
		expectedJournalRow.setDebit(debit);
		expectedJournalRow.setCredit(credit);
		expectedJournalRow.setAmount(1000);
		
		// Début du test
		actualJournalRow = (JournalRow) journalRowController.postJournal(expectedJournalRow).getBody();
		assertEquals(expectedJournalRow.getLabel(),actualJournalRow.getLabel());
		assertEquals(expectedJournalRow.getAmount(),actualJournalRow.getAmount(),0f);
		assertEquals(expectedJournalRow.getCredit().getLabel(),actualJournalRow.getCredit().getLabel());
		assertEquals(expectedJournalRow.getDebit().getLabel(),actualJournalRow.getDebit().getLabel());
		
		// Supprimer les comptes crées
		journalRowRepository.deleteById(actualJournalRow.getId());
		
    }
	
	@Test // Modifier une écriture comptable
    public void putJournalRow() throws Exception{
		
        final Long id = 15L;
        Long actualid ;
		JournalRow actualJournalRow;
		JournalRow expectedJournalRow = new JournalRow ();
				
		Account debit = new Account ();
		debit.setLabel("Debit Label "+1);
		debit.setNumber("Debit Number "+1);
		debit=accountRepository.saveAndFlush(debit);
				
		Account credit = new Account ();
		credit.setLabel("Credit Label "+1);
		credit.setNumber("Credit Number "+1);
		credit=accountRepository.saveAndFlush(credit);
		
		expectedJournalRow.setDateOperation(new Date(01,01,1990)); 
		expectedJournalRow.setLabel("Label "+1);
		expectedJournalRow.setDebit(debit);
		expectedJournalRow.setCredit(credit);
		expectedJournalRow.setAmount(1000);
		
		// Ajouter des comptes
		actualid = journalRowRepository.saveAndFlush(expectedJournalRow).getId();
		
		expectedJournalRow.setId(actualid);

		expectedJournalRow.setDateOperation(new Date(01,01,1990)); 
		expectedJournalRow.setLabel("Label Modifier"+1);
		expectedJournalRow.setAmount(1005);
		
		// Début du test
		actualJournalRow = (JournalRow) journalRowController.putJournal(expectedJournalRow).getBody();
		assertEquals(expectedJournalRow.getLabel(),actualJournalRow.getLabel());
		assertEquals(expectedJournalRow.getAmount(),actualJournalRow.getAmount(),0f);
		assertEquals(expectedJournalRow.getCredit().getLabel(),actualJournalRow.getCredit().getLabel());
		assertEquals(expectedJournalRow.getDebit().getLabel(),actualJournalRow.getDebit().getLabel());
		
		// Supprimer les comptes crées
		journalRowRepository.deleteById(actualid);
		
    }
	
	@Test  // Supprimer une écriture comptable
    public void delJournalRow() throws Exception{
		
		 	final Long id = 20L;
		 	Long actualid ;
			String actual;
			String expected = "Success";
			JournalRow journalRow = new JournalRow ();
			
			Account debit = new Account ();
			debit.setLabel("Debit Label "+1);
			debit.setNumber("Debit Number "+1);
			debit=accountRepository.saveAndFlush(debit);
					
			Account credit = new Account ();
			credit.setLabel("Credit Label "+1);
			credit.setNumber("Credit Number "+1);
			credit=accountRepository.saveAndFlush(credit);
			
			journalRow.setDateOperation(new Date(01,01,1990)); 
			journalRow.setLabel("Label "+1);
			journalRow.setDebit(debit);
			journalRow.setCredit(credit);
			journalRow.setAmount(1000);
			
			journalRow = journalRowRepository.saveAndFlush(journalRow);
			
			// Ajouter des comptes
			actualid = journalRowRepository.saveAndFlush(journalRow).getId();
			
			// Début du test
			actual = (String) journalRowController.delJournal(actualid).getBody();
			assertEquals(expected,actual);

    }
	
}
