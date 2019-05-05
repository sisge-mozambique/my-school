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

import mz.sisge.modelo.Enumeracao.EnumPeriodo;

@Entity
@Table(name = "tbl_vaga")
public class Vaga implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private int quantidade;
	private int anoLectivo;
	private Classe classe;
	private EnumPeriodo periodo;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotNull
	@Column(name = "quantidade", length = 10, nullable = false)
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@NotNull
	@Column(name = "anoLectivo", length = 5, nullable = false)
	public int getAnoLectivo() {
		return anoLectivo;
	}

	public void setAnoLectivo(int anoLectivo) {
		this.anoLectivo = anoLectivo;
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

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "periodo", nullable = false)
	public EnumPeriodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(EnumPeriodo periodo) {
		this.periodo = periodo;
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
		Vaga other = (Vaga) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
