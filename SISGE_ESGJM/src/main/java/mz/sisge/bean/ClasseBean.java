package mz.sisge.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Classe;
import mz.sisge.modelo.Enumeracao.EnumAceitacao;
import mz.sisge.modelo.Enumeracao.EnumNivelEscolar;
import mz.sisge.repositorio.ClasseRepo;
import mz.sisge.service.ClasseService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class ClasseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Classe classeCadastro;
	@Inject
	private Classe classeSelecionada;
	@Inject
	private ClasseRepo classeRepo;
	@Inject
	private ClasseService classeService;

	// LISTAS
	private List<Classe> classesLista;
	private List<Classe> classesFiltro;

	// GETS E SETS
	public Classe getClasseCadastro() {
		return classeCadastro;
	}

	public void setClasseCadastro(Classe classeCadastro) {
		this.classeCadastro = classeCadastro;
	}

	public Classe getClasseSelecionada() {
		return classeSelecionada;
	}

	public void setClasseSelecionada(Classe classeSelecionada) {
		this.classeSelecionada = classeSelecionada;
	}

	public List<Classe> getClassesLista() {
		return classesLista;
	}

	public void setClassesLista(List<Classe> classesLista) {
		this.classesLista = classesLista;
	}

	public List<Classe> getClassesFiltro() {
		return classesFiltro;
	}

	public void setClassesFiltro(List<Classe> classesFiltro) {
		this.classesFiltro = classesFiltro;
	}

	public EnumAceitacao[] getAceitacoes() {
		return EnumAceitacao.values();
	}

	public EnumNivelEscolar[] getNiveisEscolares() {
		return EnumNivelEscolar.values();
	}

	// METODOS
	public void guardar() {
		try {
			classeService.guardar(classeCadastro);
			classeCadastro = new Classe();
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@PostConstruct
	public void listar() {
		classesLista = classeRepo.listarClasses();
	}

	public void apagar() {
		try {
			classeCadastro = classeRepo.buscar(classeCadastro.getCodigo());
			classeRepo.apagar(classeCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}

}
