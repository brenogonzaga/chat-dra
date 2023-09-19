package edu.ifam.dra.chat.controller.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;

@Data
public class MensagemDTO {
    private String conteudo;
    private Long emissor;
    private Long receptor;

    public Optional<String> validate() {
        List<String> errorMessages = new ArrayList<>();
        if (conteudo == null || conteudo.isEmpty()) {
            errorMessages.add("Conteúdo não pode ser vazio");
        }
        if (emissor == null) {
            errorMessages.add("Emissor não pode ser vazio");
        }
        if (receptor == null) {
            errorMessages.add("Receptor não pode ser vazio");
        }
        if (!errorMessages.isEmpty()) {
            String errorMessage = String.join(". ", errorMessages);
            return Optional.of(errorMessage);
        }
        return Optional.empty();
    }

}