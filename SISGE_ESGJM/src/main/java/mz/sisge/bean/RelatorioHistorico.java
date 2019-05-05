package mz.sisge.bean;

import java.io.Serializable;
import java.util.Date;
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
public class RelatorioHistorico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void imprimir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);

		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/logs_report.jasper", this.response, parametros,
				"log_report_" + dataInicio + "_" + dataFim + ".pdf");

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
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
