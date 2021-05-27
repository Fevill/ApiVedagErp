package tim.vedagerp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tim.vedagerp.api.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	


}
