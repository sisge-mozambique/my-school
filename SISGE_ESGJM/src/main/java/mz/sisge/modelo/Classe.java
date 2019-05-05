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

import mz.sisge.modelo.Enumeracao.EnumAceitacao;
import mz.sisge.modelo.Enumeracao.EnumNivelEscolar;

@Entity(name = "tbl_classe")
public class Classe implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String descricao;
	private EnumNivelEscolar nivel;
	private EnumAceitacao exame;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotEmpty
	@Column(name = "descricao", length = 50, nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "nivel", length = 20, nullable = false)
	public EnumNivelEscolar getNivel() {
		return nivel;
	}

	public void setNivel(EnumNivelEscolar nivel) {
		this.nivel = nivel;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "exame", length = 20, nullable = false)
	public EnumAceitacao getExame() {
		return exame;
	}

	public void setExame(EnumAceitacao exame) {
		this.exame = exame;
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
		Classe other = (Classe) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
