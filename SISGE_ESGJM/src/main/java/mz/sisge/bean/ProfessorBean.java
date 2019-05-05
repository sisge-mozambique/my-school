package mz.sisge.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;

import mz.sisge.modelo.Professor;
import mz.sisge.modelo.Enumeracao.EnumCategoria;
import mz.sisge.modelo.Enumeracao.EnumEstado;
import mz.sisge.modelo.Enumeracao.EnumSexo;
import mz.sisge.repositorio.ProfessorRepo;
import mz.sisge.service.ProfessorService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class ProfessorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Professor professorCadastro;
	@Inject
	private Professor professorSelecionado;
	@Inject
	private ProfessorRepo professorRepo;
	@Inject
	private ProfessorService professorService;

	// LISTAS
	private List<Professor> professoresLista;
	private List<Professor> professoresFiltro;
	private List<Professor> listaImagem;

	// GETS E SETS
	public Professor getProfessorCadastro() {
		return professorCadastro;
	}

	public void setProfessorCadastro(Professor professorCadastro) {
		this.professorCadastro = professorCadastro;
	}

	public List<Professor> getProfessoresLista() {
		return professoresLista;
	}

	public void setProfessoresLista(List<Professor> professoresLista) {
		this.professoresLista = professoresLista;
	}

	public List<Professor> getProfessoresFiltro() {
		return professoresFiltro;
	}

	public void setProfessoresFiltro(List<Professor> professoresFiltro) {
		this.professoresFiltro = professoresFiltro;
	}

	public EnumCategoria[] getCategorias() {
		return EnumCategoria.values();
	}

	public EnumSexo[] getEnumSexos() {
		return EnumSexo.values();
	}

	public EnumEstado[] getEstados() {
		return EnumEstado.values();
	}

	public List<Professor> getListaImagem() {
		return listaImagem;
	}

	public void setListaImagem(List<Professor> listaImagem) {
		this.listaImagem = listaImagem;
	}

	public Professor getProfessorSelecionado() {
		return professorSelecionado;
	}

	public void setProfessorSelecionado(Professor professorSelecionado) {
		this.professorSelecionado = professorSelecionado;
	}

	// METODOS
	public void guardar() {
		try {
			professorService.guardar(professorCadastro);
			professorCadastro = new Professor();
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@PostConstruct
	public void listar() {
		professoresLista = professorRepo.listarProfessores();
	}

	public void apagar() {
		try {
			professorCadastro = professorRepo.buscar(professorCadastro
					.getCodigo());
			professorRepo.apagar(professorCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	// SALVAR IMAGEM
	public void processoCarregamentoImg(FileUploadEvent uploadEvent) {
		try {
			professorCadastro.setImagem(uploadEvent.getFile().getContents());
			FacesUtil.adicionarMensagemInfo("Imagem carregada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getImages() throws IOException {

		List<Professor> listaLocais = professorRepo.listarProfessores();
		List<String> images = new ArrayList<String>();

		String path = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/temp");

		for (Professor local : listaLocais) {
			FileOutputStream fos = new FileOutputStream(path + "/"
					+ local.getNome() + ".jpg");
			fos.write(local.getImagem());
			fos.close();
			images.add(local.getNome() + ".jpg");
		}

		return images;
	}

	public void listarImagem() {
		try {
			ServletContext context = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			listaImagem = professorRepo.listarProfessores();
			File folder = new File(context.getRealPath("/temp"));
			if (!folder.exists())
				folder.mkdirs();
			for (Professor professor : listaImagem) {
				String nomeArquivo = professor.getCodigo() + ".jpg";
				String arquivo = context.getRealPath("/temp") + File.separator
						+ nomeArquivo;
				criarArquivo(professor.getImagem(), arquivo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void criarArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
