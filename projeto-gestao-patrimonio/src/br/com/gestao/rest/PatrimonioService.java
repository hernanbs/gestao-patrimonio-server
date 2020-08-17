package br.com.gestao.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.gestao.dao.PatrimonioDAO;
import br.com.gestao.entity.Patrimonio;


@Path("/patrimonios")
public class PatrimonioService {
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private PatrimonioDAO patrDAO;
	
	@PostConstruct
	private void init() {
		this.patrDAO = new PatrimonioDAO();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Patrimonio> listPatrimonio() {
		List<Patrimonio> lista = null;
		try {
			lista = patrDAO.listPatrimonio();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String addPatrimonio(Patrimonio patrimonio) {
		System.out.println("Entrada");
		String msg = "";
		try {
			patrimonio.createNumTombo();
			int idResult = patrDAO.addPatrimonio(patrimonio);
			msg = String.valueOf(idResult);
		} catch (Exception e) {
			msg = "{ \"msg\": \"Erro ao adicionar patrimonio.\"}";
			e.printStackTrace();
		}

		return msg;
	}
	
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Patrimonio findById(@PathParam("id") int idPatrimonio) {
		Patrimonio patrimonio = null;
		try {
			patrimonio = patrDAO.findPatrimonioById(idPatrimonio);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return patrimonio;
	}
	
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String updatePatrimonio(Patrimonio patrimonio, @PathParam("id") int idPatrimonio) {
		String msg = "";

		try {
			patrDAO.updatePatrimonio(patrimonio, idPatrimonio);
			msg = "{ \"msg\": \"Patrimonio editado com sucesso!\"}";
		} catch (Exception e) {
			msg = "{ \"msg\": \"Erro ao editar Patrimonio.\"}";
			e.printStackTrace();
		}

		return msg;
	}
	
	

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String deletePatrimonio(@PathParam("id") int idPatrimonio) {
		String msg = "";

		try {
			patrDAO.deletePatrimonio(idPatrimonio);
			msg = "{ \"msg\": \"Patrimonio removido com sucesso!\"}";
		} catch (Exception e) {
			msg = "{ \"msg\": \"Erro ao remover Patrimonio.\"}";
			e.printStackTrace();
		}

		return msg;
	}
}
