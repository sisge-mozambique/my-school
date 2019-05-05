package mz.sisge.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_aluno_turma")
public class Aluno_Turma implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private Aluno aluno;
	private Turma turma;

	// LIST
	private List<Caderneta> cadernetas;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "aluno_FK", referencedColumnName = "codigo")
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "turma_FK", referencedColumnName = "codigo")
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	// LISTAS APAGAR
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
	public List<Caderneta> getCadernetas() {
		return cadernetas;
	}

	public void setCadernetas(List<Caderneta> cadernetas) {
		this.cadernetas = cadernetas;
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
		Aluno_Turma other = (Aluno_Turma) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
