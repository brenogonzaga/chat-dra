package edu.ifam.dra.chat.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import edu.ifam.dra.chat.controller.DTO.ContatoDTO;
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
	ResponseEntity<List<ContatoDTO>> getContatos() {
		List<Contato> contatos = contatoService.getContatos();
		List<ContatoDTO> contatosDTO = contatos.stream().map(ContatoDTO::new).toList();
		if (contatos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contatosDTO);
		}
		return ResponseEntity.ok(contatosDTO);

	}

	@GetMapping("/{id}")
	@Operation(summary = "Retorna um contato", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Contato"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	ResponseEntity<ContatoDTO> getContato(@PathVariable Long id) {
		Contato contato = contatoService.getContato(id);
		if (contato == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ContatoDTO());
		}
		ContatoDTO contatoDTO = new ContatoDTO(contato);
		return ResponseEntity.ok(contatoDTO);
	}

	@PostMapping
	@Operation(summary = "Cria um contato", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Contato criado"),
			@ApiResponse(responseCode = "400", description = "Contato inválido") })
	ResponseEntity<ContatoDTO> setContato(@RequestBody Contato contato) {
		Contato newContato = contatoService.setContato(contato);
		ContatoDTO contatoDTO = new ContatoDTO(newContato);
		return ResponseEntity.created(null).body(contatoDTO);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um contato", method = "PUT")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Contato atualizado"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	ResponseEntity<ContatoDTO> setContato(@RequestBody Contato contato, @PathVariable Long id) {
		try {
			Contato updatedContato = contatoService.updateContato(id, contato);
			ContatoDTO ContatoDTO = new ContatoDTO(updatedContato);
			return ResponseEntity.accepted().body(ContatoDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um contato", method = "DELETE")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Contato deletado"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	ResponseEntity<String> deleteContato(@PathVariable Long id) {
		try {
			contatoService.deleteContato(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contato não encontrado");
		}
	}

}
