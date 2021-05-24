package tim.vedagerp.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.model.Ibalance;
import tim.vedagerp.api.model.Iresultat;

public interface JournalRowRepository extends JpaRepository<JournalRow, Long> {

	List<JournalRow> findByCreditId(Long id);

	List<JournalRow> findByDebitId(Long id);

	/*
	 * @Query("SELECT c.label,c.number, SUM(j1.amount) as debit " +
	 * "FROM accounts AS c LEFT JOIN journal AS j1  ON j1.debit.id =c.id")
	 * List<Object[]> countTotalCommentsByYear();
	 */

	@Query(value = "SELECT * FROM v_balance", nativeQuery = true)
	List<Ibalance> getBalance();

	@Query(value = "  SELECT b.number, b.label, cat.*, b.debit - b.credit AS sold   FROM v_balance b"
			+ "   LEFT JOIN category AS cat ON cat.id = b.category_id  WHERE cat.prime = 'Charges'", nativeQuery = true)
	List<Iresultat> getResultatCharges();
	
	@Query(value = "  SELECT b.number, b.label, cat.*, b.credit - b.debit AS sold   FROM v_balance b"
			+ "   LEFT JOIN category AS cat ON cat.id = b.category_id  WHERE cat.prime = 'Produits'", nativeQuery = true)
	List<Iresultat> getResultatProduits();

}
