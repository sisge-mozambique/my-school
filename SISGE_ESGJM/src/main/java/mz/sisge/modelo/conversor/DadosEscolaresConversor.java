package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.DadosEscolares;
import mz.sisge.repositorio.DadosEscolaresRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = DadosEscolares.class)
public class DadosEscolaresConversor implements Converter {

	private DadosEscolaresRepo dadosEscolaresRepo;

	public DadosEscolaresConversor() {
		this.dadosEscolaresRepo = (DadosEscolaresRepo) CDIServiceLocator
				.getBean(DadosEscolaresRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		DadosEscolares retorno = null;

		if (value != null) {
			retorno = this.dadosEscolaresRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			DadosEscolares dadosEscolares = (DadosEscolares) value;
			return dadosEscolares.getCodigo() == null ? null : dadosEscolares
					.getCodigo().toString();

		}
		return "";
	}

}
