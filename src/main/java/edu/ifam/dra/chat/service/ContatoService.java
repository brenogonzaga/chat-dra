/*
 * Breno Gonzaga
 * Julianne Castro
 */
package edu.ifam.dra.chat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ifam.dra.chat.model.Contato;
import edu.ifam.dra.chat.repositories.ContatoRepository;

@Service
public class ContatoService {

	private final ContatoRepository contatoRepository;

	public ContatoService(ContatoRepository contatoRepository) {
		this.contatoRepository = contatoRepository;
	}

	public List<Contato> getContatos() {
		return contatoRepository.findAll();
	}

	public Contato getContato(Long id) {
		return contatoRepository.findById(id).orElse(null);
	}

	public Contato setContato(Contato contato) {
		return contatoRepository.save(contato);
	}

	public Contato updateContato(Long id, Contato contato) {
		Optional<Contato> findContato = contatoRepository.findById(id);
		if (findContato.isPresent()) {
			contato.setId(id);
			return contatoRepository.save(contato);
		}
		return null;
	}

	public void deleteContato(Long id) {
		contatoRepository.deleteById(id);
	}
}