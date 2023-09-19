package edu.ifam.dra.chat.controller.DTO;

import lombok.Data;

@Data
public class MensagemDTO {
    private String conteudo;
    private Long emissor;
    private Long receptor;
}