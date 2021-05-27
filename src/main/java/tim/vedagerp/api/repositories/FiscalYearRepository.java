package tim.vedagerp.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.FiscalYear;

public interface FiscalYearRepository extends JpaRepository<FiscalYear, Long> {

	Page<FiscalYear> findAllByNamespaceId(Pageable pageable, Long id);

	List<FiscalYear> findAllByNamespaceId(Long id);
}
