package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Aluno;
import mz.sisge.repositorio.AlunoRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Aluno.class)
public class AlunoConversor implements Converter {

	private AlunoRepo alunoRepo;

	public AlunoConversor() {
		this.alunoRepo = (AlunoRepo) CDIServiceLocator.getBean(AlunoRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Aluno retorno = null;

		if (value != null) {
			retorno = this.alunoRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Aluno aluno = (Aluno) value;
			return aluno.getCodigo() == null ? null : aluno.getCodigo()
					.toString();

		}
		return "";
	}

}
