package edu.ifam.dra.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifam.dra.chat.model.Mensagem;
import edu.ifam.dra.chat.repositories.MensagemRepository;

@Service
public class MensagemService {
    @Autowired
    MensagemRepository mensagemRepository;

    public Mensagem getMensagem(Long id) {
        return mensagemRepository.findById(id).orElse(null);
    }

    public Mensagem setMensagem(Mensagem msg) {
        Mensagem mensagem = mensagemRepository.save(msg);
        return mensagem;
    }

}
