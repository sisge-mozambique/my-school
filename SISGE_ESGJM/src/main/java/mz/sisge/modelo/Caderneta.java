package mz.sisge.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "tbl_cadernetaTry")
public class Caderneta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;

	// MID TRIM 1
	private BigDecimal AC1_I;
	private BigDecimal AC2_I;
	private BigDecimal AS1_I;
	private BigDecimal AS2_I;
	private BigDecimal APT_I;
	private BigDecimal media_I;

	// MID TRIM 2
	private BigDecimal AC1_II;
	private BigDecimal AC2_II;
	private BigDecimal AS1_II;
	private BigDecimal AS2_II;
	private BigDecimal APT_II;
	private BigDecimal media_II;

	// MID TRIM 3
	private BigDecimal AC1_III;
	private BigDecimal AC2_III;
	private BigDecimal AS1_III;
	private BigDecimal AS2_III;
	private BigDecimal APT_III;
	private BigDecimal media_III;

	private Aluno_Turma aluno;
	private Professor_Disciplina_Classe disciplina;
	private BigDecimal mediaGeral;
	private int faltas;
	private int anoLectivo;

	private String referenciaCaderneta;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AC1_I", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAC1_I() {
		return AC1_I;
	}

	public void setAC1_I(BigDecimal aC1_I) {
		AC1_I = aC1_I;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AC2_I", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAC2_I() {
		return AC2_I;
	}

	public void setAC2_I(BigDecimal aC2_I) {
		AC2_I = aC2_I;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AS1_I", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAS1_I() {
		return AS1_I;
	}

	public void setAS1_I(BigDecimal aS1_I) {
		AS1_I = aS1_I;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AS2_I", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAS2_I() {
		return AS2_I;
	}

	public void setAS2_I(BigDecimal aS2_I) {
		AS2_I = aS2_I;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "APT_I", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAPT_I() {
		return APT_I;
	}

	public void setAPT_I(BigDecimal aPT_I) {
		APT_I = aPT_I;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_I", nullable = false, scale = 2, precision = 5)
	public BigDecimal getMedia_I() {
		return media_I;
	}

	public void setMedia_I(BigDecimal media_I) {

		BigDecimal AC1 = new BigDecimal(0);
		BigDecimal AC2 = new BigDecimal(0);
		BigDecimal AS1 = new BigDecimal(0);
		BigDecimal AS2 = new BigDecimal(0);
		BigDecimal APT = new BigDecimal(0);

		AC1 = this.AC1_I;
		AC2 = this.AC2_I;
		AS1 = this.AS1_I;
		AS2 = this.AS2_I;
		APT = this.APT_I;

		try {
			media_I = ((((AC1.add(AC2)).divide(new BigDecimal(2), 2, 2))
					.add(((AS1.add(AS2)).divide(new BigDecimal(2), 2, 2))))
					.add(APT)).divide(new BigDecimal(3), 2, 2);

			this.media_I = media_I;
			this.mediaGeral = ((getMedia_I().add(this.getMedia_II()).add(this
					.getMedia_III())).divide(new BigDecimal(3), 2, 2));

		} catch (Exception e) {
			System.out.println(media_I + "  " + e.getMessage());
		}
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AC1_II", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAC1_II() {
		return AC1_II;
	}

	public void setAC1_II(BigDecimal aC1_II) {
		AC1_II = aC1_II;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AC2_II", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAC2_II() {
		return AC2_II;
	}

	public void setAC2_II(BigDecimal aC2_II) {
		AC2_II = aC2_II;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AS1_II", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAS1_II() {
		return AS1_II;
	}

	public void setAS1_II(BigDecimal aS1_II) {
		AS1_II = aS1_II;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AS2_II", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAS2_II() {
		return AS2_II;
	}

	public void setAS2_II(BigDecimal aS2_II) {
		AS2_II = aS2_II;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "APT_II", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAPT_II() {
		return APT_II;
	}

	public void setAPT_II(BigDecimal aPT_II) {
		APT_II = aPT_II;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_II", nullable = false, scale = 2, precision = 5)
	public BigDecimal getMedia_II() {
		return media_II;
	}

	public void setMedia_II(BigDecimal media_II) {
		BigDecimal AC1 = new BigDecimal(0);
		BigDecimal AC2 = new BigDecimal(0);
		BigDecimal AS1 = new BigDecimal(0);
		BigDecimal AS2 = new BigDecimal(0);
		BigDecimal APT = new BigDecimal(0);

		AC1 = this.AC1_II;
		AC2 = this.AC2_II;
		AS1 = this.AS1_II;
		AS2 = this.AS2_II;
		APT = this.APT_II;

		try {
			media_II = ((((AC1.add(AC2)).divide(new BigDecimal(2), 2, 2))
					.add(((AS1.add(AS2)).divide(new BigDecimal(2), 2, 2))))
					.add(APT)).divide(new BigDecimal(3), 2, 2);

			this.media_II = media_II;
			this.mediaGeral = ((getMedia_I().add(this.getMedia_II()).add(this
					.getMedia_III())).divide(new BigDecimal(3), 2, 2));

		} catch (Exception e) {
			System.out.println(media_II + "  " + e.getMessage());
		}
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AC1_III", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAC1_III() {
		return AC1_III;
	}

	public void setAC1_III(BigDecimal aC1_III) {
		AC1_III = aC1_III;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AC2_III", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAC2_III() {
		return AC2_III;
	}

	public void setAC2_III(BigDecimal aC2_III) {
		AC2_III = aC2_III;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AS1_III", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAS1_III() {
		return AS1_III;
	}

	public void setAS1_III(BigDecimal aS1_III) {
		AS1_III = aS1_III;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "AS2_III", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAS2_III() {
		return AS2_III;
	}

	public void setAS2_III(BigDecimal aS2_III) {
		AS2_III = aS2_III;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "APT_III", nullable = false, scale = 2, precision = 5)
	public BigDecimal getAPT_III() {
		return APT_III;
	}

	public void setAPT_III(BigDecimal aPT_III) {
		APT_III = aPT_III;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_III", nullable = false, scale = 2, precision = 5)
	public BigDecimal getMedia_III() {
		return media_III;
	}

	public void setMedia_III(BigDecimal media_III) {

		BigDecimal AC1 = new BigDecimal(0);
		BigDecimal AC2 = new BigDecimal(0);
		BigDecimal AS1 = new BigDecimal(0);
		BigDecimal AS2 = new BigDecimal(0);
		BigDecimal APT = new BigDecimal(0);

		AC1 = this.AC1_III;
		AC2 = this.AC2_III;
		AS1 = this.AS1_III;
		AS2 = this.AS2_III;
		APT = this.APT_III;

		try {
			media_III = ((((AC1.add(AC2)).divide(new BigDecimal(2), 2, 2))
					.add(((AS1.add(AS2)).divide(new BigDecimal(2), 2, 2))))
					.add(APT)).divide(new BigDecimal(3), 2, 2);

			this.media_III = media_III;
			this.mediaGeral = ((getMedia_I().add(this.getMedia_II()).add(this
					.getMedia_III())).divide(new BigDecimal(3), 2, 2));

		} catch (Exception e) {
			System.out.println(media_III + "  " + e.getMessage());
		}

	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "mediaGeral", scale = 2, precision = 5)
	public BigDecimal getMediaGeral() {
		return mediaGeral;

	}

	public void setMediaGeral(BigDecimal mediaGeral) {
		try {
			mediaGeral = ((getMedia_I().add(this.getMedia_II()).add(this
					.getMedia_III())).divide(new BigDecimal(3), 2, 2));
			this.mediaGeral = mediaGeral;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "aluno", referencedColumnName = "codigo")
	public Aluno_Turma getAluno() {
		return aluno;
	}

	public void setAluno(Aluno_Turma aluno) {
		this.aluno = aluno;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "disciplina", referencedColumnName = "codigo")
	public Professor_Disciplina_Classe getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Professor_Disciplina_Classe disciplina) {
		this.disciplina = disciplina;
	}

	@Column(name = "faltas", length = 5)
	public int getFaltas() {
		return faltas;
	}

	public void setFaltas(int faltas) {
		this.faltas = faltas;
	}

	@Column(name = "anoLectivo", length = 5, nullable = false)
	public int getAnoLectivo() {
		return anoLectivo;
	}

	public void setAnoLectivo(int anoLectivo) {
		this.anoLectivo = anoLectivo;
	}

	@NotEmpty
	@Column(name = "ref", length = 100, nullable = false)
	public String getReferenciaCaderneta() {
		return referenciaCaderneta;
	}

	public void setReferenciaCaderneta(String referenciaCaderneta) {

		String rf = "CAD" + this.codigo + "-" + anoLectivo + "-SISGE";
		referenciaCaderneta = rf;
		this.referenciaCaderneta = referenciaCaderneta;
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
		Caderneta other = (Caderneta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
