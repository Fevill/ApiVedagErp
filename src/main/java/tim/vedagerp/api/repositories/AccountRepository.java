package tim.vedagerp.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim.vedagerp.api.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Page<Account> findAllByNamespaceId(Pageable pageable, Long id);

	Page<Account> findAllByLabelContainsOrNumberContainsAndNamespaceIdAndAccountIsNull(Pageable pageable, String query1,
			String query2, Long id);

	Page<Account> findAllByLabelContainsOrNumberContainsAndNamespaceIdAndAccountIsNotNull(Pageable pageable,
			String query1, String query2, Long id);

	@Query(value = "SELECT * FROM accounts WHERE namespace_id=:nsId AND account_id IS null AND (accounts.label LIKE CONCAT('%',:query ,'%') OR accounts.number LIKE CONCAT('%',:query ,'%')) ", 
	countQuery = "SELECT * FROM accounts WHERE namespace_id=:nsId AND account_id IS null AND (accounts.label LIKE CONCAT('%',:query ,'%') OR accounts.number LIKE CONCAT('%',:query ,'%'))", nativeQuery = true)
	Page<Account> findByQueryNamespaceIdAccountIsNull(@Param("nsId") Long id,@Param("query")String query, Pageable pageable);

	// A Effacer
	Page<Account> findAllByNamespaceIdAndAccountIsNull(Pageable pageable, Long id);

	@Query(value = "SELECT * FROM accounts WHERE namespace_id=:nsId AND account_id IS NOT null AND (accounts.label LIKE CONCAT('%',:query ,'%') OR accounts.number LIKE CONCAT('%',:query ,'%')) ", 
	countQuery = "SELECT * FROM accounts WHERE namespace_id=:nsId AND account_id IS NOT null AND (accounts.label LIKE CONCAT('%',:query ,'%') OR accounts.number LIKE CONCAT('%',:query ,'%'))", nativeQuery = true)
	Page<Account> findByQueryNamespaceIdAccountIsNotNull(@Param("nsId") Long id,@Param("query")String query, Pageable pageable);

	// A Effacer
	Page<Account> findAllByNamespaceIdAndAccountIsNotNull(Pageable pageable, Long id);

	List<Account> findAllByNamespaceId(Long id);

	List<Account> findAllByNamespaceIdAndAccountIsNull(Long id);

	List<Account> findAllByNamespaceIdAndAccountIsNotNull(Long id);

}
