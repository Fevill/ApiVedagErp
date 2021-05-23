package tim.vedagerp.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.model.Ibalance;

public interface JournalRowRepository extends JpaRepository<JournalRow, Long> {

	List<JournalRow> findByCreditId(Long id);

	List<JournalRow> findByDebitId(Long id);
	
	/*@Query("SELECT c.label,c.number, SUM(j1.amount) as debit "
			+ "FROM accounts AS c LEFT JOIN journal AS j1  ON j1.debit.id =c.id")
	List<Object[]> countTotalCommentsByYear();*/
	
	@Query(
			  value = "SELECT * FROM v_balance", 
			  nativeQuery = true)
	List<Ibalance> countTotalCommentsByYear();

}
