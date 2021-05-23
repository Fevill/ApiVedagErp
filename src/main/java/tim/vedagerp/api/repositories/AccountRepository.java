package tim.vedagerp.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim.vedagerp.api.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	


}
