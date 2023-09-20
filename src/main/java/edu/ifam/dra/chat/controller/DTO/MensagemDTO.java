/*
 * Breno Gonzaga
 * Julianne Castro
 */

package edu.ifam.dra.chat.controller.DTO;

import edu.ifam.dra.chat.model.Mensagem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MensagemDTO {
    Long id;
    String conteudo;
    ContatoDTO emissor;
    ContatoDTO receptor;

    public MensagemDTO(Mensagem mensagem) {
        this.id = mensagem.getId();
        this.conteudo = mensagem.getConteudo();
        this.emissor = new ContatoDTO(mensagem.getEmissor());
        this.receptor = new ContatoDTO(mensagem.getReceptor());
    }
}
