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
@RequestMapping(value = "/contato")
@Tag(name = "Contato", description = "API para gerenciar contatos")
public class ContatoController {

	private final ContatoService contatoService;

	public ContatoController(ContatoService contatoService) {
		this.contatoService = contatoService;
	}

	@PostMapping
	@Operation(summary = "Cria um contato")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Contato criado"),
			@ApiResponse(responseCode = "400", description = "Contato inválido")
	})
	ResponseEntity<String> setContato(@RequestBody Contato contato) {
		try {
			Contato newContato = contatoService.setContato(contato);
			return ResponseEntity.created(null).body("Contato criado com o ID: " + newContato.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping()
	@Operation(summary = "Retorna uma lista de contatos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de contatos"),
			@ApiResponse(responseCode = "404", description = "Lista de contatos vazia")
	})
	public ResponseEntity<List<ContatoDTO>> getContatos() {
		List<Contato> contatos = contatoService.getContatos();

		List<ContatoDTO> contatosDTO = contatos.stream().map(ContatoDTO::new).toList();
		if (contatos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(contatosDTO);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Retorna um contato pelo ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Contato encontrado"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado")
	})
	ResponseEntity<ContatoDTO> getContato(@PathVariable Long id) {
		try {
			Contato contato = contatoService.getContato(id);
			ContatoDTO contatoDTO = new ContatoDTO(contato);
			return ResponseEntity.ok(contatoDTO);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um contato pelo ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Contato atualizado"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado")
	})
	ResponseEntity<String> setContato(@RequestBody Contato contato, @PathVariable Long id) {
		try {
			Contato updatedContato = contatoService.updateContato(id, contato);
			return ResponseEntity.accepted().body("Contato do usuário " + updatedContato.getNome() + " atualizado");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um contato", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Contato deletado"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado")
	})
	ResponseEntity<String> deleteContato(@PathVariable Long id) {
		try {
			contatoService.deleteContato(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
