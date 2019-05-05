package mz.sisge.bean;

import java.io.Serializable;
import java.util.List;









import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mz.sgfv.repositorio.filtro.UsuarioFilter;

import mz.sisge.modelo.Usuario;
import mz.sisge.repositorio.UsuariosRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;


@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuariosRepo usuarios;
	
	private UsuarioFilter filtro;
	
	private List<Usuario> usuariosFiltrados;
	
	private Usuario usuarioSelecionado;
	
	public PesquisaUsuarioBean(){
		filtro = new UsuarioFilter();
	}
	
	public void pesquisar(){
		usuariosFiltrados = usuarios.filtrados(filtro);
	}
	
	public void excluir(){ 
		try{
		usuarios.remover(usuarioSelecionado);
		usuariosFiltrados.remove(usuarioSelecionado);
		
		FacesUtil.adicionarMensagemInfo("Usuario" + usuarioSelecionado.getNome()
				+ "excluido com sucesso");
		}catch (ExcepcaoServico e){
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	public List<Usuario> getUsuariosFiltrados(){
		return usuariosFiltrados;
	}
	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
