package edu.ifam.dra.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MensagemService mensagemService;
    @Autowired
    ContatoService contatoService;

    @PostMapping()
    public ResponseEntity<Object> setMensagem(@RequestBody MensagemDTO msg) {
        Contato emissor = contatoService.getContato(msg.getEmissor());
        if (emissor == null) {
            return ResponseEntity.status(404).body("Emissor não encontrado");
        }

        Contato receptor = contatoService.getContato(msg.getReceptor());
        if (receptor == null) {
            return ResponseEntity.status(404).body("Receptor não encontrado");
        }
        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(msg.getConteudo());
        mensagem.setEmissor(emissor);
        mensagem.setReceptor(receptor);
        return ResponseEntity.ok(mensagemService.setMensagem(mensagem));
    }
}
