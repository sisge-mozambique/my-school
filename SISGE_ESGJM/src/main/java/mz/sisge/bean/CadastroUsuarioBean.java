package mz.sisge.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Grupo;
import mz.sisge.modelo.Usuario;
import mz.sisge.repositorio.GruposRepo;
import mz.sisge.service.CadastroUsuarioService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Grupo grupoSelecionado;

	private List<Grupo> grupos;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	@Inject
	private GruposRepo repositoryGrupos;

	public CadastroUsuarioBean() {
		usuario = new Usuario();
		grupos = new ArrayList<Grupo>();
	}

	@PostConstruct
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			grupos = repositoryGrupos.previlegios();

			if (this.grupos != null) {
				this.getGrupos();
			}
		}
	}

	public void salvar() {
		try {
			this.usuario = cadastroUsuarioService.salvar(this.usuario);
			limpar();
			FacesUtil.adicionarMensagemInfo("Usuario salvo com sucesso!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void adicionaGrupo() {
		this.usuario.getGrupos().add(this.grupoSelecionado);
		FacesUtil.adicionarMensagemInfo("Grupo adicionado com sucesso!");
	}

	public void limpar() {
		grupos = new ArrayList<Grupo>();
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;

		if (this.usuario != null) {
			this.grupos = this.usuario.getGrupos();
		}
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;

	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}
}
