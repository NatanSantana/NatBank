package entity;

import java.util.Scanner;
public class AppTeste {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("---BEM-VINDO AO NATBANK---" + "\nSaldo: " + "R$" + banco.resgatarSaldo() + "\n[1] Criar Conta\n[2] Depositar Dinheiro\n[3] Sacar Dinheiro\n[4] Transferir Dinheiro Para Outra Conta\n[5] Deletar Conta\n[6] Entrar em uma Conta\n[7] Sair da Conta\n[8] Extrato");


            String opcao = sc.nextLine();
            switch (opcao) {
                case "1":
                    System.out.println("---Criar Conta---");
                    banco.cadastrarContas();
                    break;
                case "2":
                    System.out.println("---Depositar---");
                    banco.depositar();
                    break;
                case "3":
                    System.out.println("--Sacar---");
                    banco.sacar();
                    break;
                case "4":
                    System.out.println("---Transferência Bancária---");
                    banco.transferirDinheiro();
                    break;
                case "5":
                    System.out.println("---Deletar Conta---");
                    banco.deletarConta();
                    break;
                case "6":
                    System.out.println("---LOGIN---");
                    banco.logarConta();
                    break;
                case "7":
                    System.out.println("---LOGOUT---");
                    banco.deslogar();
                    break;
                case "8":
                    System.out.println("---Extrato---");
                    banco.extrato();
                    break;
            }
        } while (true);
    }
}