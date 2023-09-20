/*
 * Breno Gonzaga
 * Julianne Castro
 */

package edu.ifam.dra.chat.controller.DTO;

import edu.ifam.dra.chat.model.Contato;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContatoDTO {
    private String nome;
    private String email;

    public ContatoDTO(Contato contato) {
        this.nome = contato.getNome();
        this.email = contato.getEmail();
    }
}
