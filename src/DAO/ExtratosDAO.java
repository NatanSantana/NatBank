package DAO;

import conexao.Conexao;
import tabelas.Extratos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExtratosDAO extends Extratos {
    public void inserirDados(Extratos extratos) {
        String sql = "INSERT INTO extratos (cpf, ato, dinheiro, cpf_terceiro) VALUES (?, ?, ?, ?)";
        PreparedStatement ps;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, extratos.getCpf());
            ps.setString(2, extratos.getAto());
            ps.setDouble(3, extratos.getDinheiro());
            ps.setString(4, extratos.getCpf_terceiro());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object extrato(String cpfDigitado) {
        String sql = "SELECT ato, dinheiro FROM extratos WHERE cpf = ?";
        PreparedStatement ps;
        ArrayList<Object> infoExtrato = new ArrayList<>();
        String ato;
        double dinheiro;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cpfDigitado);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ato = rs.getString("ato");
                dinheiro = rs.getDouble("dinheiro");
                infoExtrato.add(ato + ": "+ "R$" + dinheiro);
            }

            System.out.println("----------");
            for (Object info : infoExtrato) {

                System.out.println(info);
                System.out.println("----------");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return infoExtrato;

    }

}
