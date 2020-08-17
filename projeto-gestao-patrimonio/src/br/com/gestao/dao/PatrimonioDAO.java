package br.com.gestao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gestao.connection.ConnectionFactory;
import br.com.gestao.entity.Patrimonio;


public class PatrimonioDAO {
	public List<Patrimonio> listPatrimonio() throws Exception {
		List<Patrimonio> lista = new ArrayList<>();

		Connection conexao = ConnectionFactory.openConnection();

		String sql = "SELECT * FROM TB_PATRIMONIO";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Patrimonio patr = new Patrimonio();
			patr.setId(rs.getInt("PATRIMONIO_ID"));
			patr.setIdMarca(rs.getInt("PATRIMONIO_MARCA_ID"));
			patr.setNome(rs.getString("PATRIMONIO_NOME"));
			patr.setDescricao(rs.getString("PATRIMONIO_DESCRICAO"));
			patr.setNumTombo(rs.getInt("PATRIMONIO_NUMERO_TOMBO"));

			lista.add(patr);
		}

		return lista;
	}
	
	
	public Patrimonio findPatrimonioById(int idPatrimonio) throws Exception {
		Patrimonio patr = null;

		Connection conexao = ConnectionFactory.openConnection();

		String sql = "SELECT * FROM TB_PATRIMONIO WHERE PATRIMONIO_ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idPatrimonio);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			patr = new Patrimonio();
			patr.setId(rs.getInt("PATRIMONIO_ID"));
			patr.setIdMarca(rs.getInt("PATRIMONIO_MARCA_ID"));
			patr.setNome(rs.getString("PATRIMONIO_NOME"));
			patr.setDescricao(rs.getString("PATRIMONIO_DESCRICAO"));
			patr.setNumTombo(rs.getInt("PATRIMONIO_NUMERO_TOMBO"));
		}

		return patr;
	}
	
	public int addPatrimonio(Patrimonio patrimonio) throws Exception {
		int idResult = 0;
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "INSERT INTO TB_PATRIMONIO("
								+ "PATRIMONIO_MARCA_ID,"
								+ "PATRIMONIO_NOME,"
								+ "PATRIMONIO_DESCRICAO,"
								+ "PATRIMONIO_NUMERO_TOMBO) VALUES(?,?,?,?)";
		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setInt(1, patrimonio.getIdMarca());
		statement.setString(2, patrimonio.getNome());
		statement.setString(3, patrimonio.getDescricao());
		statement.setInt(4, patrimonio.getNumTombo());
		statement.executeUpdate();
		
		ResultSet rs = statement.getGeneratedKeys();
		
		if (rs.next()) {
			idResult = rs.getInt(1);
		}

		return idResult;
	}
	
	public void updatePatrimonio(Patrimonio patrimonio, int idPatrimonio) throws Exception {
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "UPDATE TB_PATRIMONIO SET PATRIMONIO_MARCA_ID = ?,"
												+ "PATRIMONIO_NOME = ?,"
												+ "PATRIMONIO_DESCRICAO = ?"
												+ " WHERE PATRIMONIO_ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, patrimonio.getIdMarca());
		statement.setString(2, patrimonio.getNome());
		statement.setString(3, patrimonio.getDescricao());
		
		statement.setInt(4, idPatrimonio);
		
		statement.execute();
	}
	
	public void deletePatrimonio(int idPatrimonio) throws Exception {
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "DELETE FROM TB_PATRIMONIO WHERE PATRIMONIO_ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idPatrimonio);
		statement.execute();
	}
	
	
	public List<Patrimonio> findPatrimonioByMarcas(int idMarca) throws Exception {
		List<Patrimonio> lista = new ArrayList<>();
		
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "SELECT * FROM TB_PATRIMONIO WHERE PATRIMONIO_MARCA_ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idMarca);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Patrimonio patr = new Patrimonio();
			patr = new Patrimonio();
			patr.setId(rs.getInt("PATRIMONIO_ID"));
			patr.setIdMarca(rs.getInt("PATRIMONIO_MARCA_ID"));
			patr.setNome(rs.getString("PATRIMONIO_NOME"));
			patr.setDescricao(rs.getString("PATRIMONIO_DESCRICAO"));
			patr.setNumTombo(rs.getInt("PATRIMONIO_NUMERO_TOMBO"));
			
			lista.add(patr);
		}

		return lista;
	}
}
