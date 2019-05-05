package mz.sisge.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tbl_historico_usuario")
public class Historico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String alunoNome;
	private String aluno;
	private String disciplina;
	private String classe;
	private int anoLectivo;
	private Date data;
	private String emailUsuario;

	@Id
	@GeneratedValue
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotEmpty
	@Column(name = "aluno", nullable = false, length = 100)
	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	@NotEmpty
	@Column(name = "aluno_Nome", nullable = false, length = 100)
	public String getAlunoNome() {
		return alunoNome;
	}

	public void setAlunoNome(String alunoNome) {
		this.alunoNome = alunoNome;
	}

	@NotEmpty
	@Column(name = "disciplina", nullable = false, length = 100)
	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	@NotEmpty
	@Column(name = "classe", nullable = false, length = 100)
	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	@NotNull
	@Column(name = "anoLectivo", nullable = false, length = 100)
	public int getAnoLectivo() {
		return anoLectivo;
	}

	public void setAnoLectivo(int anoLectivo) {
		this.anoLectivo = anoLectivo;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_Evento")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@NotEmpty
	@Column(name = "emailUser", nullable = false, length = 100)
	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
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
		Historico other = (Historico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
