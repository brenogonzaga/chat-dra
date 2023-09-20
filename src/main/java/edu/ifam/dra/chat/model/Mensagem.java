/*
 * Breno Gonzaga
 * Julianne Castro
 */
package edu.ifam.dra.chat.model;

import java.util.Calendar;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mensagem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CreationTimestamp
	private Calendar dataHora;
	private String conteudo;
	@ManyToOne
	private Contato emissor;
	@ManyToOne
	private Contato receptor;

	public Mensagem(String conteudo, Contato emissor, Contato receptor) {
		this.conteudo = conteudo;
		this.emissor = emissor;
		this.receptor = receptor;
	}
}
