package edu.ifam.dra.chat.controller.DTO;

import edu.ifam.dra.chat.model.Mensagem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MensagemGetDTO {
    Long id;
    String conteudo;
    ContatoDTO emissor;
    ContatoDTO receptor;

    public MensagemGetDTO(Mensagem mensagem) {
        this.id = mensagem.getId();
        this.conteudo = mensagem.getConteudo();
        this.emissor = new ContatoDTO(mensagem.getEmissor());
        this.receptor = new ContatoDTO(mensagem.getReceptor());
    }
}
