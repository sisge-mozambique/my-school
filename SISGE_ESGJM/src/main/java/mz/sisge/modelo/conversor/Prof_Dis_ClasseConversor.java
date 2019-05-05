package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Professor_Disciplina_Classe;
import mz.sisge.repositorio.Pro_Dis_ClasseRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Professor_Disciplina_Classe.class)
public class Prof_Dis_ClasseConversor implements Converter {

	private Pro_Dis_ClasseRepo pro_Dis_ClasseRepo;

	public Prof_Dis_ClasseConversor() {
		this.pro_Dis_ClasseRepo = (Pro_Dis_ClasseRepo) CDIServiceLocator
				.getBean(Pro_Dis_ClasseRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Professor_Disciplina_Classe retorno = null;

		if (value != null) {
			retorno = this.pro_Dis_ClasseRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Professor_Disciplina_Classe professor_Disciplina_Classe = (Professor_Disciplina_Classe) value;
			return professor_Disciplina_Classe.getCodigo() == null ? null
					: professor_Disciplina_Classe.getCodigo().toString();

		}
		return "";
	}

}
