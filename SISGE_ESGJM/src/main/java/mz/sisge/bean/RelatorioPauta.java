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
public class RelatorioPauta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long escola;
	private Long turma;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void imprimir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("escola", this.escola);
		parametros.put("turma", this.turma);

		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/Pauta-10.jasper", this.response, parametros,
				"Pauta-10.pdf");

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
	public Long getEscola() {
		return escola;
	}

	public void setEscola(Long escola) {
		this.escola = escola;
	}

	@NotNull
	public Long getTurma() {
		return turma;
	}

	public void setTurma(Long turma) {
		this.turma = turma;
	}

}
