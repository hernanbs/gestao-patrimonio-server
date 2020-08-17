package br.com.gestao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gestao.connection.ConnectionFactory;
import br.com.gestao.entity.Marca;

public class MarcaDAO {

	public List<Marca> listMarcas() throws Exception {
		List<Marca> lista = new ArrayList<>();

		Connection conexao = ConnectionFactory.openConnection();

		String sql = "SELECT * FROM TB_MARCA";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Marca marca = new Marca();
			marca.setId(rs.getInt("MARCA_ID"));
			marca.setNome(rs.getString("NOME"));

			lista.add(marca);
		}

		return lista;
	}
	
	
	public Marca findMarcaById(int idMarca) throws Exception {
		Marca marca = null;

		Connection conexao = ConnectionFactory.openConnection();

		String sql = "SELECT * FROM TB_MARCA WHERE MARCA_ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idMarca);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			marca = new Marca();
			marca.setId(rs.getInt("MARCA_ID"));
			marca.setNome(rs.getString("NOME"));
		}

		return marca;
	}
	
	public int addMarca(Marca marca) throws Exception {
		int idResult = 0;
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "INSERT INTO TB_MARCA(NOME) VALUES(?)";
		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, marca.getNome());
		statement.executeUpdate();
		ResultSet rs = statement.getGeneratedKeys();
		
		if (rs.next()) {
			idResult = rs.getInt(1);
		}

		return idResult;
	}
	
	public void updateMarca(Marca marca, int idMarca) throws Exception {
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "UPDATE TB_MARCA SET NOME = ? WHERE MARCA_ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, marca.getNome());
		statement.setInt(2, idMarca);
		statement.execute();
	}
	
	public void deleteMarca(int idMarca) throws Exception {
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "DELETE FROM TB_MARCA WHERE MARCA_ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idMarca);
		statement.execute();
	}
	
	public boolean existMarca(String nomeMarca) throws Exception {
		System.out.println("Entrando");
		Connection conexao = ConnectionFactory.openConnection();

		String sql = "SELECT COUNT(*) as QTD FROM TB_MARCA WHERE NOME = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, nomeMarca);
		
		ResultSet rs = statement.executeQuery();
		
		int qtdMarcas = -1;
		rs.next();
		qtdMarcas = rs.getInt(1);
		
		return qtdMarcas > 0;
	}
}
