package mz.sisge.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.sisge.modelo.Enumeracao.EnumCategoria;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tbl_dados_da_escola")
public class DadosEscolares implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String nomeEscola;
	private String nomeDirector;
	private String chefeSecretaria;
	private EnumCategoria nivelChefeSecretaria;
	private String curso;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotEmpty
	@Column(name = "nome_escola", length = 200, nullable = false)
	public String getNomeEscola() {
		return nomeEscola;
	}

	public void setNomeEscola(String nomeEscola) {
		this.nomeEscola = nomeEscola;
	}

	@NotEmpty
	@Column(name = "director", length = 100, nullable = false)
	public String getNomeDirector() {
		return nomeDirector;
	}

	public void setNomeDirector(String nomeDirector) {
		this.nomeDirector = nomeDirector;
	}

	@NotEmpty
	@Column(name = "chefe_secretaria", length = 100, nullable = false)
	public String getChefeSecretaria() {
		return chefeSecretaria;
	}

	public void setChefeSecretaria(String chefeSecretaria) {
		this.chefeSecretaria = chefeSecretaria;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "chefe_secretaria_nivel", nullable = false)
	public EnumCategoria getNivelChefeSecretaria() {
		return nivelChefeSecretaria;
	}

	public void setNivelChefeSecretaria(EnumCategoria nivelChefeSecretaria) {
		this.nivelChefeSecretaria = nivelChefeSecretaria;
	}

	@NotEmpty
	@Column(name = "chefe_secretaria_curso", length = 100, nullable = false)
	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
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
		DadosEscolares other = (DadosEscolares) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
