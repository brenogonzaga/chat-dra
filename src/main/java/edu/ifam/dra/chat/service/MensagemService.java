/*
 * Breno Gonzaga
 * Julianne Castro
 */
package edu.ifam.dra.chat.service;

import org.springframework.stereotype.Service;

import edu.ifam.dra.chat.model.Mensagem;
import edu.ifam.dra.chat.repositories.MensagemRepository;

@Service
public class MensagemService {
    private final MensagemRepository mensagemRepository;

    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public Mensagem getMensagem(Long id) {
        return mensagemRepository.findById(id).orElse(null);
    }

    public Mensagem setMensagem(Mensagem msg) {
        return mensagemRepository.save(msg);
    }

    public Mensagem updateMensagem(Long id, Mensagem msg) {
        Mensagem mensagem = mensagemRepository.findById(id).orElse(null);
        if (mensagem == null) {
            return null;
        }
        mensagem.setConteudo(msg.getConteudo());
        mensagem.setEmissor(msg.getEmissor());
        mensagem.setReceptor(msg.getReceptor());
        return mensagemRepository.save(mensagem);
    }

    public void deleteMensagem(Long id) {
        mensagemRepository.deleteById(id);
    }

}
