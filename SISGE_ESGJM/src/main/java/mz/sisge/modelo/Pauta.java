package mz.sisge.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotEmpty;

import mz.sisge.modelo.Enumeracao.EnumPassagem;

@Entity
@Table(name = "tbl_pauta")
public class Pauta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;

	// DISCIPLINAS
	private Aluno_Turma aluno_Turma;
	private EnumPassagem enumPassagem;
	private BigDecimal media_Fim;

	private BigDecimal notaExame;
	private BigDecimal media_FimExame;
	private EnumPassagem enumPassagemExame;
	private String referenciaPauta;

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
	public Aluno_Turma getAluno_Turma() {
		return aluno_Turma;
	}

	public void setAluno_Turma(Aluno_Turma aluno_Turma) {
		this.aluno_Turma = aluno_Turma;
	}

	@Enumerated(EnumType.STRING)
	public EnumPassagem getEnumPassagem() {
		return enumPassagem;
	}

	public void setEnumPassagem(EnumPassagem enumPassagem) {

		this.enumPassagem = enumPassagem;

	}

	@NotEmpty
	@Column(name = "ref", length = 100, nullable = false)
	public String getReferenciaPauta() {
		return referenciaPauta;
	}

	public void setReferenciaPauta(String referenciaPauta) {
		String ref = "PT" + this.codigo + "-"
				+ this.aluno_Turma.getAluno().getVaga().getAnoLectivo()
				+ "-ALT" + this.aluno_Turma.getCodigo() + "-SISGE";
		System.out.println("REFERENCIA = " + ref);
		referenciaPauta = ref;
		this.referenciaPauta = referenciaPauta;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_Fim", scale = 2, precision = 5)
	public BigDecimal getMedia_Fim() {
		return media_Fim;
	}

	public void setMedia_Fim(BigDecimal media_Fim) {
		this.media_Fim = media_Fim;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "notaExame", scale = 2, precision = 5)
	public BigDecimal getNotaExame() {
		return notaExame;
	}

	public void setNotaExame(BigDecimal notaExame) {
		this.notaExame = notaExame;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_Fim_Exame", scale = 2, precision = 5)
	public BigDecimal getMedia_FimExame() {
		return media_FimExame;
	}

	public void setMedia_FimExame(BigDecimal media_FimExame) {
		this.media_FimExame = media_FimExame;
	}
	
	@Enumerated(EnumType.STRING)
	public EnumPassagem getEnumPassagemExame() {
		return enumPassagemExame;
	}
	
	public void setEnumPassagemExame(EnumPassagem enumPassagemExame) {
		this.enumPassagemExame = enumPassagemExame;
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
		Pauta other = (Pauta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
