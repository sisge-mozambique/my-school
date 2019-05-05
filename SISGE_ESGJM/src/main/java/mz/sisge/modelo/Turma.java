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

import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.modelo.Enumeracao.EnumTurma;

@Entity(name = "tbl_turma")
public class Turma implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private EnumTurma nomeTurma;
	private int anoLectivo;
	private Sala sala;
	private Classe classe;

	private EnumPeriodo periodo;
	private String director;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public EnumTurma getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(EnumTurma nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "sala", referencedColumnName = "codigo")
	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	@Column(name = "director", length = 50)
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@NotNull
	@Column(name = "anoLectivo", length = 10)
	public int getAnoLectivo() {
		return anoLectivo;
	}

	public void setAnoLectivo(int anoLectivo) {
		this.anoLectivo = anoLectivo;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "periodo", length = 20)
	public EnumPeriodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(EnumPeriodo periodo) {
		this.periodo = periodo;
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
		Turma other = (Turma) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
