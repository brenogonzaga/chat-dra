package edu.ifam.dra.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifam.dra.chat.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

}
