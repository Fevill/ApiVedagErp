package tim.vedagerp.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tim.vedagerp.api.entities.NameSpace;
import tim.vedagerp.api.model.Message;
import tim.vedagerp.api.services.NameSpaceService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/ns")
public class NameSpaceController {

	@Autowired
	NameSpaceService nameSpaceService;
	
	private static Logger logger = LogManager.getLogger(NameSpaceController.class);

	@GetMapping()
	public ResponseEntity<?> getNameSpace(
		@RequestParam("sort") String sort,
		@RequestParam("order") String order,
		@RequestParam("page") int page,
		@RequestParam("size") int size,
		@RequestParam("query") String query ) {
		logger.info("getNameSpace");
		Page<NameSpace> namespace = nameSpaceService.listSortOrder(sort,order,page,size,query);
		return new ResponseEntity<>(namespace, HttpStatus.OK);
	}
	

	@GetMapping("/all")
	public ResponseEntity<?> getNameSpaceAll() {
		logger.info("getNameSpace");
		List<NameSpace> namespace = nameSpaceService.list();
		return new ResponseEntity<>(namespace, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getNameSpace(@PathVariable("id") long id) {
		logger.info("getNameSpace");
		try {
			NameSpace namespace = nameSpaceService.get(id);
			return new ResponseEntity<>(namespace, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			return new ResponseEntity<>(String.format("Pas de valeur pour id: %d", id), HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<?> postNameSpace(@RequestBody NameSpace body) {
		logger.info("postNameSpace");
		try {
			NameSpace namespace = nameSpaceService.add(body);
			return new ResponseEntity<>(namespace, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}
	
	@PostMapping("/all")
	public ResponseEntity<?> postNameSpaceAll(@RequestBody List<NameSpace> body) {
		logger.info("postNameSpaceAll");
		try {
			List<NameSpace> namespace = nameSpaceService.addAll(body);
			return new ResponseEntity<>(namespace, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@PutMapping()
	public ResponseEntity<?> putNameSpace(@RequestBody NameSpace body) {
		logger.info("putNameSpace");
		try {
			NameSpace namespace = nameSpaceService.update(body);
			return new ResponseEntity<>(namespace, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delNameSpace(@PathVariable("id") long id) {
		logger.info("delNameSpace");

		try {
			Message res = new Message();
			res.setText(nameSpaceService.delete(id));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<>(String.format("Id %d n'existe pas.", id), HttpStatus.OK);
		}

	}

}
