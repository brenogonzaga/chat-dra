/*
 * Breno Gonzaga
 * Julianne Castro
 */
package edu.ifam.dra.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ifam.dra.chat.controller.DTO.MensagemValidateDTO;
import edu.ifam.dra.chat.controller.DTO.MensagemDTO;
import edu.ifam.dra.chat.model.Contato;
import edu.ifam.dra.chat.model.Mensagem;
import edu.ifam.dra.chat.service.ContatoService;
import edu.ifam.dra.chat.service.MensagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/mensagem")
@Tag(name = "Mensagem", description = "API para gerenciar mensagens")
public class MensagemController {

    private final MensagemService mensagemService;
    private final ContatoService contatoService;

    public MensagemController(MensagemService mensagemService, ContatoService contatoService) {
        this.mensagemService = mensagemService;
        this.contatoService = contatoService;
    }

    @PostMapping()
    @Operation(summary = "Cria uma nova mensagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mensagem criada"),
            @ApiResponse(responseCode = "400", description = "Mensagem inválida"),
            @ApiResponse(responseCode = "404", description = "Emissor ou receptor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar mensagem")
    })
    public ResponseEntity<String> setMensagem(@RequestBody MensagemValidateDTO msg) {
        if (msg.validate().isPresent()) {
            return ResponseEntity.badRequest().body(msg.validate().get());
        }

        Contato emissor = contatoService.getContato(msg.getEmissor());
        if (emissor == null) {
            return ResponseEntity.status(404).body("Emissor não encontrado");
        }

        Contato receptor = contatoService.getContato(msg.getReceptor());
        if (receptor == null) {
            return ResponseEntity.status(404).body("Receptor não encontrado");
        }

        Mensagem mensagem = new Mensagem(msg.getConteudo(), emissor, receptor);
        try {
            mensagemService.setMensagem(mensagem);
            return ResponseEntity.ok("Mensagem criada");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar mensagem");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma mensagem", method = "GET")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Mensagem"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada") })
    public ResponseEntity<MensagemDTO> getMensagem(@PathVariable Long id) {
        try {
            Mensagem mensagem = mensagemService.getMensagem(id);
            MensagemDTO mensagemDTO = new MensagemDTO(mensagem);
            return ResponseEntity.ok(mensagemDTO);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
}