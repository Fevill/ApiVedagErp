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

import tim.vedagerp.api.entities.FiscalYear;
import tim.vedagerp.api.repositories.FiscalYearRepository;

@Service
public class FiscalYearService {

	@Autowired
	FiscalYearRepository fiscalyearRepository;

	// Liste des années fiscales
	// Recherche sur le nom de l'exercices*/
	public Page<FiscalYear> listSortOrder(String sort, String order, int page, int size,Long id,String query) {
		Pageable pageable = null;
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}
		return fiscalyearRepository.findAllByNamespaceIdAndNameContains(pageable,id,query);
	}

	// Liste des années fiscales
	public List<FiscalYear> list() {

		return fiscalyearRepository.findAll();
	}

	// Récupérer une année fiscale avec son id
	public FiscalYear get(long id) throws NoSuchElementException {
		return fiscalyearRepository.findById(id).get();
	}

	// Ajouter une année fiscale 
	public FiscalYear add(FiscalYear body) {
		return fiscalyearRepository.saveAndFlush(body);
	}

	// Modifier une année fiscale 
	public FiscalYear update(FiscalYear body) {
		return fiscalyearRepository.saveAndFlush(body);
	}

	// Supprimer une année fiscale 
	public String delete(long id) throws EmptyResultDataAccessException {
		fiscalyearRepository.deleteById(id);
		return "Success";
	}

	// Ajouter une liste d'une année fiscale 
	public List<FiscalYear> addAll(List<FiscalYear> body) {
		return fiscalyearRepository.saveAll(body);
	}

	public List<FiscalYear> listByNamespaceId(long id) {
		return fiscalyearRepository.findAllByNamespaceId(id);
	}

}
