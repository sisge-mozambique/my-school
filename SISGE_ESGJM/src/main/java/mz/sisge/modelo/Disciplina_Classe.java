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
import javax.validation.constraints.NotNull;

import mz.sisge.modelo.Enumeracao.EnumAvaliacaoDisc_Classe;

@Entity(name = "tbl_Disciplina_Classe")
public class Disciplina_Classe implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private Classe classe;
	private Disciplina disciplina;
	private EnumAvaliacaoDisc_Classe avaliacao;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "classe_FK", referencedColumnName = "codigo")
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "disciplina_FK", referencedColumnName = "codigo")
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "avaliacao", length = 20)
	public EnumAvaliacaoDisc_Classe getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(EnumAvaliacaoDisc_Classe avaliacao) {
		this.avaliacao = avaliacao;
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
		Disciplina_Classe other = (Disciplina_Classe) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
