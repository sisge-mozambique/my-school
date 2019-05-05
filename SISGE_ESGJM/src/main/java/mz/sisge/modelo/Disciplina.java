package mz.sisge.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import mz.sisge.modelo.Enumeracao.EnumDisciplinaGrupo;

@Entity(name = "tbl_disciplina")
public class Disciplina implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String nome;
	private String sigla;
	private String ref;
	private EnumDisciplinaGrupo grupo;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotEmpty
	@Column(name = "nome", length = 100, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty
	@Column(name = "sigla", length = 50, nullable = false)
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "grupo", length = 20, nullable = false)
	public EnumDisciplinaGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(EnumDisciplinaGrupo grupo) {
		this.grupo = grupo;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		String codDisc = "UC-" + this.codigo + "-" + this.sigla + "SISGE";
		ref = codDisc;
		this.ref = ref;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
