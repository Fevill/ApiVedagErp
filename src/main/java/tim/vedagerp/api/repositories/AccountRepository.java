package tim.vedagerp.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	Page<Account> findAllByNamespaceId(Pageable pageable, Long id);

	List<Account> findAllByNamespaceId(Long id);
	


}
