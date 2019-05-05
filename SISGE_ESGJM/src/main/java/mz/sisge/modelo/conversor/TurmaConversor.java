package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Turma;
import mz.sisge.repositorio.TurmaRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Turma.class)
public class TurmaConversor implements Converter {

	private TurmaRepo turmaRepo;

	public TurmaConversor() {
		this.turmaRepo = (TurmaRepo) CDIServiceLocator.getBean(TurmaRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Turma retorno = null;

		if (value != null) {
			retorno = this.turmaRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Turma turma = (Turma) value;
			return turma.getCodigo() == null ? null : turma.getCodigo()
					.toString();

		}
		return "";
	}

}
