package tim.vedagerp.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.NameSpace;
import tim.vedagerp.api.repositories.NameSpaceRepository;

@Service
public class NameSpaceService {

	@Autowired
	NameSpaceRepository nameSpaceRepository;

	// Liste des espaces de nom
	public Page<NameSpace> listSortOrder(String sort, String order, int page, int size,String query) {
		Pageable pageable = null;
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}
		return nameSpaceRepository.findAllByNameContainsOrDescriptionContains(query,query,pageable);
	}

	// Liste des espaces de nom
	public List<NameSpace> list() {

		return nameSpaceRepository.findAll();
	}

	// Récupérer un espace de nom avec son id
	public NameSpace get(long id) throws NoSuchElementException {
		return nameSpaceRepository.findById(id).get();
	}

	// Ajouter un espace de nom
	public NameSpace add(NameSpace body) {
		return nameSpaceRepository.saveAndFlush(body);
	}

	// Modifier un espace de nom
	public NameSpace update(NameSpace body) {
		return nameSpaceRepository.saveAndFlush(body);
	}

	// Supprimer un espace de nom
	public String delete(long id) throws EmptyResultDataAccessException {
		nameSpaceRepository.deleteById(id);
		return "Success";
	}

	// Ajouter une liste d'espace de nom
	public List<NameSpace> addAll(List<NameSpace> body) {
		return nameSpaceRepository.saveAll(body);
	}

}
