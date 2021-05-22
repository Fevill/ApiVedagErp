package tim.vedagerp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.JournalRow;

public interface JournalRowRepository extends JpaRepository<JournalRow, Long> {

}
