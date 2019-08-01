package com.legato.sbr.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legato.sbr.dao.BookRepository;
import com.legato.sbr.model.Book;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository repo;

	@GetMapping
	public ResponseEntity<List<Book>> getAll() {
		return new ResponseEntity<List<Book>>(repo.findAll(), OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<Book> getById(@PathVariable(name = "id") long id) {
		ResponseEntity<Book> resp;
		Optional<Book> b = repo.findById(id);

		if (b.isPresent())
			resp = new ResponseEntity<>(b.get(), OK);
		else
			resp = new ResponseEntity<>(NOT_FOUND);
		return resp;

	}

	@PostMapping
	public ResponseEntity<Book> create(@RequestBody Book b) {
		ResponseEntity<Book> resp;

		if (repo.existsById(b.getBcode())) {
			resp = new ResponseEntity<>(CONFLICT);
		} else {
			b = repo.save(b);
			resp = new ResponseEntity<>(b, OK);
		}
		return resp;
	}

	@PutMapping
	public ResponseEntity<Book> modify(@RequestBody Book b) {
		ResponseEntity<Book> resp;

		if (repo.existsById(b.getBcode())) {
			b = repo.save(b);
			resp = new ResponseEntity<>(b, OK);
		} else {
			resp = new ResponseEntity<>(NOT_FOUND);
		}
		return resp;
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable(name = "id") long bcode) {
		ResponseEntity<Void> resp;

		if (repo.existsById(bcode)) {
			repo.deleteById(bcode);
			resp = new ResponseEntity<>(OK);
		} else {
			resp = new ResponseEntity<>(NOT_FOUND);

		}
		return resp;

	}

}
