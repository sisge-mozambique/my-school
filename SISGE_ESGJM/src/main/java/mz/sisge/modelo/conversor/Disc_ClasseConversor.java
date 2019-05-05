package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.repositorio.Disciplina_ClasseRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Disciplina_Classe.class)
public class Disc_ClasseConversor implements Converter {

	private Disciplina_ClasseRepo disciplina_ClasseRepo;

	public Disc_ClasseConversor() {
		this.disciplina_ClasseRepo = (Disciplina_ClasseRepo) CDIServiceLocator
				.getBean(Disciplina_ClasseRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Disciplina_Classe retorno = null;

		if (value != null) {
			retorno = this.disciplina_ClasseRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Disciplina_Classe disciplina_Classe = (Disciplina_Classe) value;
			return disciplina_Classe.getCodigo() == null ? null
					: disciplina_Classe.getCodigo().toString();

		}
		return "";
	}

}
