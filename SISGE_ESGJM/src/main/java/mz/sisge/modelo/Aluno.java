package mz.sisge.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import mz.sisge.modelo.Enumeracao.EnumEstado;
import mz.sisge.modelo.Enumeracao.EnumGrupo;
import mz.sisge.modelo.Enumeracao.EnumSexo;

@Entity(name = "tbl_aluno")
public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private byte[] imagem;
	private String nome;
	private Date dataNascimento;
	private EnumSexo sexo;
	private String bilhete;

	// FILIACAO
	private String nomePai;
	private String nomeMae;

	// NATURALIDADE
	private String pais;
	private String provincia;
	private String cidade;

	// MORADA
	private String bairro;
	private String rua;
	private String nrCasa;

	// CONTACTO
	private String numero_1;
	private String numero_2;
	private String numero_3;
	private String e_mail;
	private String facebook;

	private EnumEstado estado;
	private Vaga vaga;
	private EnumGrupo grupo;

	// REQUISITOS 10a
	private BigDecimal media_8a;
	private BigDecimal media_9a;

	// REQUISITOS 12a
	private BigDecimal media_11a;

	// LISTAS
	private List<Aluno_Turma> alunosTurma;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Lob
	@Column(name = "imagem")
	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	@NotEmpty
	@Column(name = "nome", length = 100, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataNascimento", nullable = false)
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "sexo", length = 20, nullable = false)
	public EnumSexo getSexo() {
		return sexo;
	}

	public void setSexo(EnumSexo sexo) {
		this.sexo = sexo;
	}

	@NotEmpty
	@Column(name = "nomePai", length = 100, nullable = false)
	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	@NotEmpty
	@Column(name = "nomeMae", length = 100, nullable = false)
	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	@NotEmpty
	@Column(name = "pais", length = 100, nullable = false)
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@NotEmpty
	@Column(name = "provincia", length = 100, nullable = false)
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@NotEmpty
	@Column(name = "cidade", length = 100, nullable = false)
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@NotEmpty
	@Column(name = "bilhete", length = 50)
	public String getBilhete() {
		return bilhete;
	}

	public void setBilhete(String bilhete) {
		this.bilhete = bilhete;
	}

	@NotEmpty
	@Column(name = "bairro", length = 100, nullable = false)
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name = "rua", length = 100)
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	@Column(name = "nrCasa", length = 20)
	public String getNrCasa() {
		return nrCasa;
	}

	public void setNrCasa(String nrCasa) {
		this.nrCasa = nrCasa;
	}

	@NotEmpty
	@Column(name = "numero_1", length = 30, nullable = false)
	public String getNumero_1() {
		return numero_1;
	}

	public void setNumero_1(String numero_1) {
		this.numero_1 = numero_1;
	}

	@Column(name = "numero_2", length = 30)
	public String getNumero_2() {
		return numero_2;
	}

	public void setNumero_2(String numero_2) {
		this.numero_2 = numero_2;
	}

	@Column(name = "numero_3", length = 30)
	public String getNumero_3() {
		return numero_3;
	}

	public void setNumero_3(String numero_3) {
		this.numero_3 = numero_3;
	}

	@Column(name = "e_mail", length = 50)
	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	@Column(name = "facebook", length = 50)
	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "grupo", length = 20, nullable = false)
	public EnumGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(EnumGrupo grupo) {
		this.grupo = grupo;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", length = 20, nullable = false)
	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
	}

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "vaga_FK", referencedColumnName = "codigo")
	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	// LISTAS APAGAR
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
	public List<Aluno_Turma> getAlunosTurma() {
		return alunosTurma;
	}

	public void setAlunosTurma(List<Aluno_Turma> alunosTurma) {
		this.alunosTurma = alunosTurma;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_8a", scale = 2, precision = 5)
	public BigDecimal getMedia_8a() {
		return media_8a;
	}

	public void setMedia_8a(BigDecimal media_8a) {
		this.media_8a = media_8a;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_9a", scale = 2, precision = 5)
	public BigDecimal getMedia_9a() {
		return media_9a;
	}

	public void setMedia_9a(BigDecimal media_9a) {
		this.media_9a = media_9a;
	}

	@DecimalMin("0")
	@DecimalMax("20")
	@Column(name = "media_11a", scale = 2, precision = 5)
	public BigDecimal getMedia_11a() {
		return media_11a;
	}

	public void setMedia_11a(BigDecimal media_11a) {
		this.media_11a = media_11a;
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
		Aluno other = (Aluno) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
