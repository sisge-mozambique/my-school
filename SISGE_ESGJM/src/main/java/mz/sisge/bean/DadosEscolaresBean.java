package mz.sisge.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.DadosEscolares;
import mz.sisge.modelo.Enumeracao.EnumCategoria;
import mz.sisge.repositorio.DadosEscolaresRepo;
import mz.sisge.service.DadosEscolaresService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class DadosEscolaresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DadosEscolares dadosEscolaresCadastro;
	@Inject
	private DadosEscolares dadosEscolaresSelecionado;
	@Inject
	private DadosEscolaresRepo dadosEscolaresRepo;
	@Inject
	private DadosEscolaresService dadosEscolaresService;

	// LISTAS
	private List<DadosEscolares> dadosEscolaresLista;
	private List<DadosEscolares> dadosEscolaresclassesFiltro;

	// GETS E SETS
	public DadosEscolares getDadosEscolaresCadastro() {
		return dadosEscolaresCadastro;
	}

	public void setDadosEscolaresCadastro(DadosEscolares dadosEscolaresCadastro) {
		this.dadosEscolaresCadastro = dadosEscolaresCadastro;
	}

	public DadosEscolares getDadosEscolaresSelecionado() {
		return dadosEscolaresSelecionado;
	}

	public void setDadosEscolaresSelecionado(
			DadosEscolares dadosEscolaresSelecionado) {
		this.dadosEscolaresSelecionado = dadosEscolaresSelecionado;
	}

	public List<DadosEscolares> getDadosEscolaresLista() {
		return dadosEscolaresLista;
	}

	public void setDadosEscolaresLista(List<DadosEscolares> dadosEscolaresLista) {
		this.dadosEscolaresLista = dadosEscolaresLista;
	}

	public List<DadosEscolares> getDadosEscolaresclassesFiltro() {
		return dadosEscolaresclassesFiltro;
	}

	public void setDadosEscolaresclassesFiltro(
			List<DadosEscolares> dadosEscolaresclassesFiltro) {
		this.dadosEscolaresclassesFiltro = dadosEscolaresclassesFiltro;
	}

	public EnumCategoria[] getCategorias() {
		return EnumCategoria.values();
	}

	// METODOS
	public void guardar() {
		try {
			dadosEscolaresService.guardar(dadosEscolaresCadastro);
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@PostConstruct
	public void listar() {
		dadosEscolaresLista = dadosEscolaresRepo.listarDadosEscolares();
	}

	public void apagar() {
		try {
			dadosEscolaresCadastro = dadosEscolaresRepo
					.buscar(dadosEscolaresCadastro.getCodigo());
			dadosEscolaresRepo.apagar(dadosEscolaresCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}
}
