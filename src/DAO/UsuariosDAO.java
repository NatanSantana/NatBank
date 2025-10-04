package DAO;

import conexao.Conexao;
import tabelas.Usuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO extends Usuarios {

    public void cadastrarUsuarios(Usuarios usuarios){
        String  sql = "INSERT INTO usuarios (cpf, nome, idade, senha, saldo) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, usuarios.getCpf());
            ps.setString(2, usuarios.getNome());
            ps.setInt(3, usuarios.getIdade());
            ps.setString(4, usuarios.getSenha());
            ps.setDouble(5, usuarios.getSaldo());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void alterarSaldo(String cpf, double saldoAdicionado){
        String sql = "UPDATE usuarios SET saldo = ? WHERE cpf = ?";
        PreparedStatement ps;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setDouble(1, saldoAdicionado);
            ps.setString(2,cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double resgatarSaldo(String cpf){
        String sql = "SELECT saldo FROM usuarios WHERE cpf = ?";
        PreparedStatement ps;
        double saldo = 0.0;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return saldo;
    }

    public void deletarConta(String cpf){
        String sql = "DELETE FROM usuarios WHERE cpf = ?";
        PreparedStatement ps;


        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse CPF.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String resgatarSenha(String cpf){
        String sql = "SELECT senha FROM usuarios WHERE cpf = ?";
        PreparedStatement ps;
        String senha = "";

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                senha = rs.getString("senha");
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return senha;
    }

    public String resgatarCpf (String cpfDigitado) {
        String sql  = "SELECT cpf FROM usuarios WHERE cpf = ?";
        PreparedStatement ps;
        String cpfResgatado = "";


        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpfDigitado);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cpfResgatado = rs.getString("cpf");
            }

            rs.close();
            ps.close();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cpfResgatado;
    }

    public String resgatarNome(String cpfDigitado) {
        String sql = "SELECT nome FROM usuarios WHERE cpf = ?";
        PreparedStatement ps;
        String nomeResgatado = "";

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1,cpfDigitado);



            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nomeResgatado = rs.getString("nome");
            }
            rs.close();
            ps.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nomeResgatado;
    }
}
