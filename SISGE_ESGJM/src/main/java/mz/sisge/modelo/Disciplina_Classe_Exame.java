package mz.sisge.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.sisge.modelo.Enumeracao.EnumAceitacao;

@Entity
@Table(name = "tbl_disc_classe_exame")
public class Disciplina_Classe_Exame implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private Disciplina_Classe disciplina;
	private EnumAceitacao exame;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "disc_classe_FK", referencedColumnName = "codigo")
	public Disciplina_Classe getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina_Classe disciplina) {
		this.disciplina = disciplina;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "exame", nullable = false)
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
		Disciplina_Classe_Exame other = (Disciplina_Classe_Exame) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
