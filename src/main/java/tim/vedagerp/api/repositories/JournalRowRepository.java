package tim.vedagerp.api.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.model.Ibalance;
import tim.vedagerp.api.model.Ibilan;
import tim.vedagerp.api.model.Iresultat;

public interface JournalRowRepository extends JpaRepository<JournalRow, Long> {

	List<JournalRow> findByCreditIdAndNamespaceIdAndDateOperationBetween(Long cid, Long nid, Date start, Date end);

	List<JournalRow> findByDebitIdAndNamespaceIdAndDateOperationBetween(Long did, Long nid, Date start, Date end);

	Page<JournalRow> findAllByNamespaceIdAndDateOperationBetween(Pageable pageable, Long id, Date start, Date end);

	List<JournalRow> findAllByNamespaceId(Long id);

	/*
	 * Recherche journal par mois en fonction de l'espace de travail et de
	 * l'exercice fiscal
	 */
	@Query(value = "SELECT * FROM journal WHERE namespace_id=:nsid AND date_operation BETWEEN :start AND :end AND EXTRACT(MONTH FROM date_operation) = :month ORDER BY date_operation", nativeQuery = true)
	List<JournalRow> getJournalByNsidFyidMonth(@Param("nsid") Long nsId, @Param("start") Date start,
			@Param("end") Date end, @Param("month") int month);

	/*
	 * @Query("SELECT c.label,c.number, SUM(j1.amount) as debit " +
	 * "FROM accounts AS c LEFT JOIN journal AS j1  ON j1.debit.id =c.id")
	 * List<Object[]> countTotalCommentsByYear();
	 */

	@Query(value = "SELECT * FROM v_balance AS b WHERE b.namespace_id=:nsid AND b.date_operation BETWEEN :start AND :end ", nativeQuery = true)
	List<Ibalance> getBalance(@Param("nsid") Long nsId, @Param("start") Date start, @Param("end") Date end);

	/*
	 * @Query(value =
	 * "SELECT * FROM v_balance AS b WHERE b.bdid = :nsid AND  b.bcid = :nsid",
	 * nativeQuery = true) List<Ibalance> getBalance(@Param("nsid") Long nsId);
	 */

	@Query(value = "SELECT b.number, b.category_id,  b.label, SUM(b.debit-b.credit) AS sold, cat.id , cat.prime , cat.second \r\n"
			+ "FROM v_balance b \r\n" + "LEFT JOIN category AS cat ON cat.id = b.category_id \r\n"
			+ "WHERE b.namespace_id=:nsid AND cat.prime = 'Charges' AND  b.date_operation \r\n"
			+ "BETWEEN :start AND :end\r\n"
			+ "GROUP BY b.number, b.category_id,b.label,  cat.id , cat.prime , cat.second", nativeQuery = true)
	List<Iresultat> getResultatCharges(@Param("nsid") Long nsId, @Param("start") Date start, @Param("end") Date end);

	@Query(value = "SELECT b.number, b.category_id,  b.label, SUM(b.credit-b.debit) AS sold, cat.id , cat.prime , cat.second \r\n"
			+ "FROM v_balance b \r\n" + "LEFT JOIN category AS cat ON cat.id = b.category_id \r\n"
			+ "WHERE b.namespace_id=:nsid AND cat.prime = 'Produits' AND  b.date_operation \r\n"
			+ "BETWEEN :start AND :end\r\n"
			+ "GROUP BY b.number, b.category_id,b.label,  cat.id , cat.prime , cat.second", nativeQuery = true)
	List<Iresultat> getResultatProduits(@Param("nsid") Long nsId, @Param("start") Date start, @Param("end") Date end);

	@Query(value = "SELECT b.number, b.category_id,  b.label, SUM(b.debit-b.credit) AS sold, cat.id , cat.prime , cat.second \r\n"
			+ "FROM v_balance b \r\n" + "LEFT JOIN category AS cat ON cat.id = b.category_id \r\n"
			+ "WHERE b.namespace_id=:nsid AND cat.prime = 'Actif' AND  b.date_operation \r\n"
			+ "BETWEEN :start AND :end\r\n"
			+ "GROUP BY b.number, b.category_id,b.label,  cat.id , cat.prime , cat.second", nativeQuery = true)
	List<Ibilan> getBilanActif(@Param("nsid") Long nsId, @Param("start") Date start, @Param("end") Date end);

	@Query(value = "SELECT b.number, b.category_id,  b.label, SUM(b.credit-b.debit) AS sold, cat.id , cat.prime , cat.second \r\n"
			+ "FROM v_balance b \r\n" + "LEFT JOIN category AS cat ON cat.id = b.category_id \r\n"
			+ "WHERE b.namespace_id=:nsid AND cat.prime = 'Passif' AND  b.date_operation \r\n"
			+ "BETWEEN :start AND :end\r\n"
			+ "GROUP BY b.number, b.category_id,b.label,  cat.id , cat.prime , cat.second", nativeQuery = true)
	List<Ibilan> getBilanPassif(@Param("nsid") Long nsId, @Param("start") Date start, @Param("end") Date end);

}
