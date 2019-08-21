package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entities.Diretor;
import model.entities.Empregado;
import model.entities.Gerente;
import model.entities.Operacional;

public class EmpregadoDAO implements BaseDAO<Empregado> {

	public static final String DESCRICAO_TIPO_EMPREGADO_OPERACIONAL = "Operacional";
	public static final String DESCRICAO_TIPO_EMPREGADO_DIRETOR = "Diretor";
	public static final String DESCRICAO_TIPO_EMPREGADO_GERENTE = "Gerente";
	public static final String TIPO_EMPREGADO_OPERACIONAL = "O";
	public static final String TIPO_EMPREGADO_DIRETOR = "D";
	public static final String TIPO_EMPREGADO_GERENTE = "G";

	public Empregado salvar(Empregado funcionario) {
		Connection conn = Banco.getConnection();
		String sqlInsert = " INSERT INTO EMPREGADO (NOME, CPF, SEXO, IDADE, SALARIOBRUTO, DESCONTOIMPOSTODERENDA, "
				+ " CONTRIBUICAOPREVIDENCIA, SALARIOBASE, SALARIOLIQUIDO, COMISSAO,TIPO) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
		PreparedStatement stmt = Banco.getPreparedStatement(conn, sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);

		try {
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setString(3, funcionario.getSexo());
			stmt.setInt(4, funcionario.getIdade());
			stmt.setDouble(5, funcionario.getSalarioBruto());
			stmt.setDouble(6, funcionario.getDescontoImpostoDeRenda());
			stmt.setDouble(7, funcionario.getContribuicaoPrevidencia());
			stmt.setDouble(8, funcionario.getSalarioBase());
			stmt.setDouble(9, funcionario.calcularSalario());

			if (funcionario instanceof Operacional) {
				stmt.setDouble(10, 0);
				stmt.setString(11, TIPO_EMPREGADO_OPERACIONAL);
			} else if (funcionario instanceof Diretor) {
				stmt.setDouble(10, ((Diretor) funcionario).getComissao());
				stmt.setString(11, TIPO_EMPREGADO_DIRETOR);
			} else if (funcionario instanceof Gerente) {
				stmt.setDouble(10, ((Gerente) funcionario).getComissao());
				stmt.setString(11, TIPO_EMPREGADO_GERENTE);
			}

			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				int idGerado = rs.getInt(1);
				funcionario.setId(idGerado);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar funcionario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);

		}
		return funcionario;
	}

	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String sqlDelete = "DELETE FROM EMPREGADO WHERE ID=" + id;

		int linhaAfetada = 0;

		try {

			linhaAfetada = stmt.executeUpdate(sqlDelete);

		} catch (SQLException e) {
			System.out.println("Erro ao deletar empregado!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return linhaAfetada > 0;
	}

	public boolean alterar(Empregado funcionarioAtualizado) {
		Connection conn = Banco.getConnection();
		String sqlUpdate = "UPDATE SET EMPREGADO (NOME= ?, CPF= ?, SEXO= ?, IDADE= ?, SALARIOBRUTO= ?"
				+ "SALARIOBASE= ?, SALARIOLIQUIDO= ?, COMISSAO=?, TIPO= ? WHERE ID=?";
		PreparedStatement stmt = Banco.getPreparedStatement(conn, sqlUpdate);

		int quantidadeRegistrosAtualizados = 0;
		try {
			stmt.setString(1, funcionarioAtualizado.getNome());
			stmt.setString(2, funcionarioAtualizado.getCpf());
			stmt.setString(3, funcionarioAtualizado.getSexo());
			stmt.setInt(4, funcionarioAtualizado.getIdade());
			stmt.setDouble(5, funcionarioAtualizado.getSalarioBruto());
			stmt.setDouble(6, funcionarioAtualizado.getSalarioBase());
			stmt.setDouble(7, funcionarioAtualizado.calcularSalario());

			if (funcionarioAtualizado instanceof Operacional) {
				stmt.setDouble(8, 0);
				stmt.setString(9, TIPO_EMPREGADO_OPERACIONAL);
			} else if (funcionarioAtualizado instanceof Diretor) {
				stmt.setDouble(8, ((Diretor) funcionarioAtualizado).getComissao());
				stmt.setString(9, TIPO_EMPREGADO_DIRETOR);
			} else if (funcionarioAtualizado instanceof Gerente) {
				stmt.setDouble(8, ((Gerente) funcionarioAtualizado).getComissao());
				stmt.setString(9, TIPO_EMPREGADO_GERENTE);
			}

			quantidadeRegistrosAtualizados = stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Erro ao atualizar empregado.");
			System.out.println("Erro: " + e.getMessage());

		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return quantidadeRegistrosAtualizados > 0;
	}

	public Empregado consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		String sqlConsulta = "SELECT * FROM EMPREGADO WHERE ID=" + id;
		Statement stmt = Banco.getStatement(conn);

		ResultSet rs = null;
		Empregado funcionario = null;

		try {

			rs = stmt.executeQuery(sqlConsulta);

			if (rs.next()) {
				String tipo = rs.getString("tipo");
				String nome = rs.getString("nome");
				String cpf = rs.getString("cpf");
				String sexo = rs.getString("sexo");
				int idade = rs.getInt("idade");
				double salarioBruto = rs.getDouble("salariobruto");
				double comissao = rs.getDouble("comissao");

				if (tipo.equals(TIPO_EMPREGADO_OPERACIONAL)) {
					funcionario = new Operacional(nome, cpf, sexo, idade, salarioBruto);
				} else if (tipo.equals(TIPO_EMPREGADO_GERENTE)) {
					funcionario = new Gerente(nome, cpf, sexo, idade, salarioBruto, comissao);
				} else if (tipo.equals(TIPO_EMPREGADO_DIRETOR)) {
					funcionario = new Diretor(nome, cpf, sexo, idade, salarioBruto, comissao);
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar empregado!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}

		return funcionario;
	}

	public ArrayList<Empregado> consultarTodos() {
		Connection conn = Banco.getConnection();
		String sqlConsulta = "SELECT * FROM EMPREGADO";
		Statement stmt = Banco.getStatement(conn);

		ArrayList<Empregado> empregados = new ArrayList<Empregado>();
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sqlConsulta);
			while (rs.next()) {

				String nome = rs.getString("nome");
				String cpf = rs.getString("cpf");
				String sexo = rs.getString("sexo");
				int idade = rs.getInt("idade");
				double salarioBruto = rs.getDouble("salariobruto");
				double comissao = rs.getDouble("comissao");
				String tipo = rs.getString("tipo");

				if (tipo.equals(TIPO_EMPREGADO_OPERACIONAL)) {
					empregados.add(new Operacional(nome, cpf, sexo, idade, salarioBruto));
				} else if (tipo.equals(TIPO_EMPREGADO_GERENTE)) {
					empregados.add(new Gerente(nome, cpf, sexo, idade, salarioBruto, comissao));
				} else if (tipo.equals(TIPO_EMPREGADO_DIRETOR)) {
					empregados.add(new Diretor(nome, cpf, sexo, idade, salarioBruto, comissao));
				}
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os empregados.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(rs);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}

		return empregados;
	}

}
