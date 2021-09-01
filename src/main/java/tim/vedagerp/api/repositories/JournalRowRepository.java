package tim.vedagerp.api.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.model.IBudgetRow;
import tim.vedagerp.api.model.IResultatMonth;
import tim.vedagerp.api.model.Ibalance;
import tim.vedagerp.api.model.ResultatRow;

public interface JournalRowRepository extends JpaRepository<JournalRow, Long> {

	List<JournalRow> findByCreditIdAndNamespaceIdAndDateOperationBetween(Long cid, Long nid, Date start, Date end);

	List<JournalRow> findByDebitIdAndNamespaceIdAndDateOperationBetween(Long did, Long nid, Date start, Date end);

	Page<JournalRow> findAllByNamespaceIdAndDateOperationBetween(Pageable pageable, Long id, Date start, Date end);

	List<JournalRow> findAllByNamespaceId(Long id);

	/*
	 * Recherche journal par mois en fonction de l'espace de travail et de
	 * l'exercice fiscal
	 */
	@Query(value = "SELECT * FROM journal WHERE namespace_id=:nsid AND date_operation BETWEEN :start AND :end AND EXTRACT(MONTH FROM date_operation) = :month ORDER BY date_operation DESC", nativeQuery = true)
	List<JournalRow> getJournalByNsidFyidMonth(@Param("nsid") Long nsId, @Param("start") Date start,
			@Param("end") Date end, @Param("month") int month);

	/*
	 * @Query("SELECT c.label,c.number, SUM(j1.amount) as debit " +
	 * "FROM accounts AS c LEFT JOIN journal AS j1  ON j1.debit.id =c.id")
	 * List<Object[]> countTotalCommentsByYear();
	 */
	/**
	 * A supprimer
	 * 
	 * @Query(value = "SELECT * FROM v_balance AS b WHERE b.namespace_id=:nsid AND
	 *              b.date_operation BETWEEN :start AND :end ", nativeQuery = true)
	 *              List<Ibalance> getBalance(@Param("nsid") Long
	 *              nsId, @Param("start") Date start, @Param("end") Date end);
	 */

	/**
	 * 
	 * @param nsId
	 * @param start
	 * @param end
	 * @return
	 */
	@Query(value = "SELECT accounts.*, COALESCE(ROUND(SUM(debit)::::numeric,2),0) as debit, "
			+ "COALESCE(ROUND(SUM(credit)::::numeric,2),0) as credit  FROM public.accounts  LEFT JOIN (  "
			+ "	SELECT * FROM public.v_balance WHERE public.v_balance.namespace_id=:nsid "
			+ "AND (date_operation  BETWEEN :start AND :end OR  date_operation ISNULL) )as b "
			+ "ON b.acc_id = public.accounts.id WHERE public.accounts.account_id IS NOT NULL AND public.accounts.namespace_id=:nsid "
			+ "GROUP BY accounts.id ORDER BY accounts.number", nativeQuery = true)
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
	List<ResultatRow> getResultatCharges(@Param("nsid") Long nsId, @Param("start") Date start, @Param("end") Date end);

	@Query(value = "SELECT b.number, b.category_id,  b.label, SUM(b.credit-b.debit) AS sold, cat.id , cat.prime , cat.second \r\n"
			+ "FROM v_balance b \r\n" + "LEFT JOIN category AS cat ON cat.id = b.category_id \r\n"
			+ "WHERE b.namespace_id=:nsid AND cat.prime = 'Produits' AND  b.date_operation \r\n"
			+ "BETWEEN :start AND :end\r\n"
			+ "GROUP BY b.number, b.category_id,b.label,  cat.id , cat.prime , cat.second", nativeQuery = true)
	List<ResultatRow> getResultatProduits(@Param("nsid") Long nsId, @Param("start") Date start, @Param("end") Date end);

	/**
	 * Solde credit d'un sous comptes
	 */
	@Query(value = "SELECT COALESCE(SUM(amount), 0) FROM journal WHERE namespace_id=:nsid AND date_operation BETWEEN :start AND :end AND credit_id=:subAccountId", nativeQuery = true)
	float getSoldeCreditByNsidFyid(@Param("nsid") Long nsId, @Param("subAccountId") Long subAccountId,
			@Param("start") Date start, @Param("end") Date end);

	/**
	 * Solde debit d'un sous comptes
	 */
	@Query(value = "SELECT COALESCE(SUM(amount), 0) FROM journal WHERE namespace_id=:nsid AND date_operation BETWEEN :start AND :end AND debit_id=:subAccountId", nativeQuery = true)
	float getSoldeDebitByNsidFyid(@Param("nsid") Long nsId, @Param("subAccountId") Long subAccountId,
			@Param("start") Date start, @Param("end") Date end);

	/*
	 * Recherche journal par mois en fonction de l'espace de travail et de
	 * l'exercice fiscal
	 */
	@Query(value = "SELECT * FROM journal " + "WHERE namespace_id=:nsid " + "AND credit_id=:accountid "
			+ "AND date_operation BETWEEN :start AND :end " + "AND EXTRACT(MONTH FROM date_operation) = :month "
			+ "UNION " + "SELECT * FROM journal " + "WHERE namespace_id=:nsid " + "AND debit_id=:accountid "
			+ "AND date_operation BETWEEN :start AND :end " + "AND EXTRACT(MONTH FROM date_operation) = :month "
			+ "ORDER BY date_operation DESC", nativeQuery = true)
	List<JournalRow> getLedger(@Param("nsid") Long nsId, @Param("accountid") Long accountid, @Param("start") Date start,
			@Param("end") Date end, @Param("month") int month);



	@Query(value = "SELECT vsCredit.mois, "
	+"(COALESCE(vsCredit.soldeCredit,0)-COALESCE(vsDebit.soldeDebit,0))  as solde,  "
	+"(COALESCE(vsCredit.soldePrevCredit,0)-COALESCE(vsDebit.soldePrevDebit,0))  as soldeprev,  "
	+"COALESCE(vsCredit.soldeCredit,0)  as affaire  FROM (  "
		+"SELECT SUM(amount) AS soldeCredit,  "
		+"SUM(amountprev) AS soldePrevCredit,  "
		+"EXTRACT(MONTH FROM date_operation) AS mois,  "
		+"vju.namespace_id FROM public.v_journal_union vju "
		+"LEFT JOIN accounts ON "
		+"accounts.id = credit_id   "
		+"LEFT JOIN fiscalyear ON  "
		+"fiscalyear.namespace_id = vju.namespace_id  "
		+"WHERE vju.namespace_id=:nsId   "
		+"AND fiscalyear.id = :fyId AND date_operation BETWEEN   "
		+"fiscalyear.start_date AND fiscalyear.end_date  "
		+"AND accounts.label_bilan = 'PRODUIT'  "
		+"GROUP BY mois,vju.namespace_id  "
		+"ORDER BY mois ) as vsCredit  "
		+"LEFT JOIN   "
		+"(SELECT * FROM (  "
			+"SELECT SUM(amount) AS soldeDebit, " 
			+"SUM(amountprev) AS soldePrevDebit,  "
			+"EXTRACT(MONTH FROM date_operation) AS mois,  " 
			+"vju.namespace_id FROM public.v_journal_union vju  "
			+"LEFT JOIN accounts ON  "
			+"accounts.id = debit_id  " 
			+"LEFT JOIN fiscalyear ON  "
			+"fiscalyear.namespace_id = vju.namespace_id " 
			+"WHERE vju.namespace_id=:nsId "
			+"AND fiscalyear.id = :fyId AND date_operation  BETWEEN  " 
			+"fiscalyear.start_date AND fiscalyear.end_date  "
			+"AND accounts.label_bilan = 'CHARGE'  "
			+"GROUP BY mois,vju.namespace_id  "
			+"ORDER BY mois) as vsDebit0 ) as vsDebit  "
			+"ON vsCredit.mois = vsDebit.mois", nativeQuery = true)
	List<IResultatMonth>  getResultatByMonth(@Param("nsId") Long nsId, @Param("fyId") Long  fyId);


	@Query(value = "SELECT accounts.id AS id, accounts.label AS label, "
	+"EXTRACT(MONTH FROM date_operation) AS month, "
	+"SUM(amount) AS solde, " 
	+"SUM(amountprev) AS soldePrev "
	+"FROM public.v_journal_union vju  " 
	+"LEFT JOIN accounts ON   "
	+"accounts.id = debit_id  " 
	+"LEFT JOIN fiscalyear ON   "
	+"fiscalyear.namespace_id = vju.namespace_id  "
	+"WHERE vju.namespace_id=:nsId  "
	+"AND fiscalyear.id = :fyId AND date_operation  BETWEEN   " 
	+":start AND :end "
	+"AND accounts.label_bilan = 'CHARGE'  " 
	+"GROUP BY  month,vju.namespace_id,accounts.id", nativeQuery = true)
	List<IBudgetRow> getBudgetCharges(@Param("nsId") Long nsId,@Param("fyId") Long fyId, @Param("start") Date start, @Param("end") Date end);

	@Query(value = "SELECT accounts.id AS id, accounts.label AS label, "
	+"EXTRACT(MONTH FROM date_operation) AS month, "
	+"SUM(amount) AS solde, "
	+"SUM(amountprev) AS soldePrev "
	+"FROM public.v_journal_union vju   "
	+"LEFT JOIN accounts ON   "
	+"accounts.id = credit_id  " 
	+"LEFT JOIN fiscalyear ON   "
	+"fiscalyear.namespace_id = vju.namespace_id " 
	+"WHERE vju.namespace_id=:nsId  "
	+"AND fiscalyear.id = :fyId AND date_operation  BETWEEN   " 
	+":start AND :end "
	+"AND accounts.label_bilan = 'PRODUIT'   "
	+"GROUP BY  month,vju.namespace_id,accounts.id", nativeQuery = true)
	List<IBudgetRow> getBudgetProduits(@Param("nsId") Long nsId,@Param("fyId") Long fyId, @Param("start") Date start, @Param("end") Date end);





}
