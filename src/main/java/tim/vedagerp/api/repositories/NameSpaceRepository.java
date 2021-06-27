package tim.vedagerp.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.NameSpace;

public interface NameSpaceRepository extends JpaRepository<NameSpace, Long> {

    Page<NameSpace> findAllByNameContainsOrDescriptionContains(String name,String description, Pageable pageable);
}
