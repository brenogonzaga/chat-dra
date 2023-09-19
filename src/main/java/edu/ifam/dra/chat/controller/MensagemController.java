package edu.ifam.dra.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ifam.dra.chat.controller.DTO.MensagemDTO;
import edu.ifam.dra.chat.model.Contato;
import edu.ifam.dra.chat.model.Mensagem;
import edu.ifam.dra.chat.service.ContatoService;
import edu.ifam.dra.chat.service.MensagemService;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {

    private final MensagemService mensagemService;
    private final ContatoService contatoService;

    public MensagemController(MensagemService mensagemService, ContatoService contatoService) {
        this.mensagemService = mensagemService;
        this.contatoService = contatoService;
    }

    @PostMapping()
    public ResponseEntity<Object> setMensagem(@RequestBody MensagemDTO msg) {
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
        return ResponseEntity.ok(mensagemService.setMensagem(mensagem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMensagem(@PathVariable Long id) {
        Mensagem mensagem = mensagemService.getMensagem(id);
        if (mensagem == null) {
            return ResponseEntity.status(404).body("Mensagem não encontrada");
        }
        return ResponseEntity.ok(mensagem);
    }
}
