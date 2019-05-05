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

import mz.sisge.modelo.Enumeracao.EnumAvaliacaoProf_Dis_Class;

@Entity(name = "tbl_Professor_Disciplina_Classe")
public class Professor_Disciplina_Classe implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private Disciplina_Classe disciplina_Classe;
	private Professor professor;
	private EnumAvaliacaoProf_Dis_Class avaliacao;

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
	@JoinColumn(name = "disciplina_Classe_FK", referencedColumnName = "codigo")
	public Disciplina_Classe getDisciplina_Classe() {
		return disciplina_Classe;
	}

	public void setDisciplina_Classe(Disciplina_Classe disciplina_Classe) {
		this.disciplina_Classe = disciplina_Classe;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "professor_FK", referencedColumnName = "codigo")
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "avaliacao", length = 20)
	public EnumAvaliacaoProf_Dis_Class getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(EnumAvaliacaoProf_Dis_Class avaliacao) {
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
		Professor_Disciplina_Classe other = (Professor_Disciplina_Classe) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
