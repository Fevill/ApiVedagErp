package tim.vedagerp.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.JournalRow;

public interface JournalRowRepository extends JpaRepository<JournalRow, Long> {

	List<JournalRow> findByCreditId(Long id);

	List<JournalRow> findByDebitId(Long id);

}
