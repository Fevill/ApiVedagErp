package tim.vedagerp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
