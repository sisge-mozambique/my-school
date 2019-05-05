package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Vaga;
import mz.sisge.repositorio.VagaRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Vaga.class)
public class VagaConversor implements Converter {

	private VagaRepo vagaRepo;

	public VagaConversor() {
		this.vagaRepo = (VagaRepo) CDIServiceLocator.getBean(VagaRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Vaga retorno = null;

		if (value != null) {
			retorno = this.vagaRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Vaga vaga = (Vaga) value;
			return vaga.getCodigo() == null ? null : vaga.getCodigo()
					.toString();

		}
		return "";
	}

}
