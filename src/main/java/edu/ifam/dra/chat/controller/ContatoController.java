package edu.ifam.dra.chat.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import edu.ifam.dra.chat.model.Contato;
import edu.ifam.dra.chat.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/contato", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Contato", description = "API para gerenciar contatos")
public class ContatoController {

	private final ContatoService contatoService;

	public ContatoController(ContatoService contatoService) {
		this.contatoService = contatoService;
	}

	@GetMapping
	@Operation(summary = "Retorna uma lista de contatos", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de contatos"),
			@ApiResponse(responseCode = "404", description = "Lista de contatos vazia") })
	ResponseEntity<List<Contato>> getContatos() {
		List<Contato> contatos = contatoService.getContatos();
		if (contatos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contatos);
		}
		return ResponseEntity.ok(contatos);

	}

	@GetMapping("/{id}")
	@Operation(summary = "Retorna um contato", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Contato"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	ResponseEntity<Contato> getContato(@PathVariable Long id) {
		Contato contato = contatoService.getContato(id);
		if (contato == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Contato());
		}
		return ResponseEntity.ok(contato);
	}

	@PostMapping
	@Operation(summary = "Cria um contato", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Contato criado"),
			@ApiResponse(responseCode = "400", description = "Contato inválido") })
	ResponseEntity<Contato> setContato(@RequestBody Contato contato) {
		Contato newContato = contatoService.setContato(contato);
		return ResponseEntity.created(null).body(newContato);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um contato", method = "PUT")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Contato atualizado"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	ResponseEntity<Contato> setContato(@RequestBody Contato contato, @PathVariable Long id) {
		try {
			return ResponseEntity.accepted().body(contatoService.updateContato(id, contato));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Contato());
		}

	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um contato", method = "DELETE")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Contato deletado"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	ResponseEntity<Contato> deleteContato(@PathVariable Long id) {
		try {
			contatoService.deleteContato(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Contato());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Contato());
		}
	}

}
