package tim.vedagerp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tim.vedagerp.api.entities.JournalRow;

@RestController("api/v1/journal")
public class JournalRowController {
	
	@GetMapping("/")
    public ResponseEntity<?> getJournal() {
        return new ResponseEntity<>("Le journal", HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getJournal(@PathVariable("id") long id) {
        return new ResponseEntity<>("La ligne "+id+" du journal", HttpStatus.OK);
    }
	
	@PostMapping("/")
    public ResponseEntity<?> postJournal(@RequestBody JournalRow body) {
        return new ResponseEntity<>("Crée une ligne journal", HttpStatus.OK);
    }
	
	@PutMapping("/")
    public ResponseEntity<?> putJournal(@RequestBody JournalRow body) {
		return new ResponseEntity<>("Modifier une ligne journal", HttpStatus.OK);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> delJournal(@PathVariable("id") long id) {
		return new ResponseEntity<>("Supprimer une ligne journal", HttpStatus.OK);
    }


}
