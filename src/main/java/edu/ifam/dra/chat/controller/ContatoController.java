package edu.ifam.dra.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import edu.ifam.dra.chat.model.Contato;
import edu.ifam.dra.chat.service.ContatoService;

@RequestMapping("/contato")
@RestController
public class ContatoController {

	@Autowired
	ContatoService contatoService;

	@GetMapping
	ResponseEntity<List<Contato>> getContatos() {
		List<Contato> contatos = contatoService.getContatos();
		if (contatos.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contatos);
		return ResponseEntity.ok(contatos);

	}

	@GetMapping("/{id}")
	ResponseEntity<Contato> getContato(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(contatoService.getContato(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Contato());
		}

	}

	@PostMapping
	ResponseEntity<Contato> setContato(@RequestBody Contato contato) {
		contatoService.setContato(contato);
		return ResponseEntity.created(null).body(contato);
	}

	@PutMapping("/{id}")
	ResponseEntity<Contato> setContato(@RequestBody Contato contato, @PathVariable Long id) {
		try {
			return ResponseEntity.accepted().body(contatoService.setContato(id, contato));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Contato());
		}

	}

	@DeleteMapping("/{id}")
	ResponseEntity<Contato> deleteContato(@PathVariable Long id) {
		try {
			contatoService.deleteContato(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Contato());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Contato());
		}
	}

}
