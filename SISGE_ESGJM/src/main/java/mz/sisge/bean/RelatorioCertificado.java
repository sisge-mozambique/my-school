package mz.sisge.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import mz.sisge.utilitario.FacesUtil;
import mz.sisge.utilitario.relatorio.ExecutorRelatorio;

import org.hibernate.Session;

@Named
@RequestScoped
public class RelatorioCertificado implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long aluno;
	private Long classe;
	private Long escola;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void imprimir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("aluno", this.aluno);
		parametros.put("classe", this.classe);
		parametros.put("escola", this.escola);

		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/Certificado.jasper", this.response, parametros,
				"Certificado_" + aluno + "-" + classe + "_.pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil
					.adicionarMensagemWarn("O relatório não retornou nenhum dado.");
		}
	}

	@NotNull
	public Long getAluno() {
		return aluno;
	}

	public void setAluno(Long aluno) {
		this.aluno = aluno;
	}

	@NotNull
	public Long getClasse() {
		return classe;
	}

	public void setClasse(Long classe) {
		this.classe = classe;
	}

	@NotNull
	public Long getEscola() {
		return escola;
	}

	public void setEscola(Long escola) {
		this.escola = escola;
	}

}
