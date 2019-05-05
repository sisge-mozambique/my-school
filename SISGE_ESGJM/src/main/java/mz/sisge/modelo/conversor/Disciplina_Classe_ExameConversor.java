package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Disciplina_Classe_Exame;
import mz.sisge.repositorio.Disciplina_Classe_ExameRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Disciplina_Classe_Exame.class)
public class Disciplina_Classe_ExameConversor implements Converter {

	private Disciplina_Classe_ExameRepo disciplina_Classe_ExameRepo;

	public Disciplina_Classe_ExameConversor() {
		this.disciplina_Classe_ExameRepo = (Disciplina_Classe_ExameRepo) CDIServiceLocator
				.getBean(Disciplina_Classe_ExameRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Disciplina_Classe_Exame retorno = null;

		if (value != null) {
			retorno = this.disciplina_Classe_ExameRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Disciplina_Classe_Exame disciplina_Classe_Exame = (Disciplina_Classe_Exame) value;
			return disciplina_Classe_Exame.getCodigo() == null ? null
					: disciplina_Classe_Exame.getCodigo().toString();

		}
		return "";
	}

}
