package DAO;


import conexao.Conexao;
import tabelas.Emprestimos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmprestimosDAO extends Emprestimos {
    public void cadastrarEmprestimos(Emprestimos emprestimo) {
        String sql = "INSERT INTO emprestimos (cpfUsuario, valorEmprestado, montanteFinal, parcelas, valorParcelas, valorRestante) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, emprestimo.getCpfUsuario());
            ps.setFloat(2, emprestimo.getValorEmprestado());
            ps.setFloat(3, emprestimo.getMontanteFinal());
            ps.setFloat(4, emprestimo.getParcelas());
            ps.setFloat(5, emprestimo.getValorParcelas());
            ps.setFloat(6, emprestimo.getValorRestante());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int resgatarNumeroParcelas(String cpf) {
        String sql = "SELECT parcelas FROM emprestimos WHERE cpfUsuario = ?";
        PreparedStatement ps;
        int numeroParcelas = 0;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                numeroParcelas = rs.getInt("parcelas");
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return numeroParcelas;
    }

    public float resgatarValorParcela(String cpf) {
        String sql = "SELECT valorParcelas FROM emprestimos WHERE cpfUsuario = ?";
        PreparedStatement ps;
        float valorParcelas = 0.0F;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                valorParcelas = rs.getFloat("valorParcelas");
            }
            rs.close();
            ps.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valorParcelas;

    }

    public void alterarNumeroParcelas(String cpf, int parcelasAtual) {
        String sql = "UPDATE emprestimos SET parcelas = ? WHERE cpfUsuario = ?";
        PreparedStatement ps;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setDouble(1, parcelasAtual);
            ps.setString(2,cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float resgatarMontanteFinal(String cpf) {
        String sql = "SELECT montanteFinal FROM emprestimos WHERE cpfUsuario = ?";
        PreparedStatement ps;
        float montanteFinal = 0.0F;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                montanteFinal = rs.getFloat("montanteFinal");
            }
            rs.close();
            ps.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return montanteFinal;
    }

    public void alterarvalorRestante(String cpf, double valorAtual) {
        String sql = "UPDATE emprestimos SET valorRestante = ? WHERE cpfUsuario = ?";
        PreparedStatement ps;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setDouble(1, valorAtual);
            ps.setString(2, cpf);

            ps.execute();
            ps.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public float resgatarValorRestante(String cpf) {
        String sql = "SELECT valorRestante FROM emprestimos WHERE cpfUsuario = ?";
        PreparedStatement ps;
        float valorRestante = 0.0F;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                valorRestante = rs.getFloat("valorRestante");
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return valorRestante;
    }

    public void deletarEmprestimo(String cpf) {
        String sql = "DELETE FROM emprestimos WHERE cpfUsuario = ?";
        PreparedStatement ps;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

