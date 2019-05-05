package mz.sisge.utilitario;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	public static boolean isNotPostback() {
		return !isPostback();
	}

	public static void adicionarMensagemInfo(String msg) {
		FacesMessage facesMensagens = new FacesMessage(
				FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext facesContext = FacesContext.getCurrentInstance();

		facesContext.addMessage(null, facesMensagens);

	}

	public static void adicionarMensagemWarn(String msg) {
		FacesMessage facesMensagens = new FacesMessage(
				FacesMessage.SEVERITY_WARN, msg, msg);
		FacesContext facesContext = FacesContext.getCurrentInstance();

		facesContext.addMessage(null, facesMensagens);

	}

	public static void adicionarMensagemErro(String msg) {
		FacesMessage facesMensagens = new FacesMessage(
				FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext facesContext = FacesContext.getCurrentInstance();

		facesContext.addMessage(null, facesMensagens);
	}

	public static String getParametro(String nomeParamentro) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		// Retorna todos os Parametros
		Map<String, String> parametros = externalContext
				.getRequestParameterMap();

		return parametros.get(nomeParamentro); // retorna Somente o Parametro
												// que Deseja
	}
}
