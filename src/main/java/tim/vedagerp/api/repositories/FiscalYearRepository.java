package tim.vedagerp.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.FiscalYear;

public interface FiscalYearRepository extends JpaRepository<FiscalYear, Long> {

	Page<FiscalYear> findAllByNamespaceIdAndNameContains(Pageable pageable, Long id,String query);

	Page<FiscalYear> findAllByNamespaceId(Pageable pageable, Long id);

	List<FiscalYear> findAllByNamespaceId(Long id);

	Optional<FiscalYear> findById(Long id);

	FiscalYear findByName(String id);
}
