package entity;
import DAO.ExtratosDAO;
import DAO.UsuariosDAO;
import tabelas.Extratos;
import tabelas.Usuarios;

import java.util.Objects;
import java.util.Scanner;

public class Banco {
    private String cpfDigitado;
    private String contaLogada = "";

    private final Usuarios user = new Usuarios();
    private final UsuariosDAO userDAO = new UsuariosDAO();
    private final ExtratosDAO extratoDAO = new ExtratosDAO();
    private final Extratos extrato = new Extratos();
    Scanner sc = new Scanner(System.in);


    public void cadastrarContas() {
        System.out.println("------------------");
        System.out.print("Nome Completo: ");
        String nome = sc.nextLine();
        user.setNome(nome);

        do {
            System.out.println("Crie uma Senha");
            String senha = sc.nextLine();

            if (senha.length() >= 5) {
                user.setSenha(senha);
                break;
            } else {
                System.out.println("Sua senha deve ter pelo menos 5 caracteres");
            }
        } while (true);

        do {
            try {
                System.out.println("------------------");
                System.out.print("Idade: ");
                int idade = Integer.parseInt(sc.nextLine());
                System.out.println("------------------");

                if (idade >= 18 && idade <= 118) {
                    user.setIdade(idade);
                    break;
                } else {
                    System.out.println("Insira uma idade válida, para criar uma conta, você precisa ser maior que 18 anos");
                }
            } catch (NumberFormatException e) {
                System.out.println("Insira APENAS NÚMEROS!");
            }
        } while (true);

        do {
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.println("------------------");

            String cpfExiste = "";

            cpfExiste = userDAO.getCpf(cpf);

            if (cpf.length() == 11 && cpfExiste.isEmpty()) {
                user.setCpf(cpf);
                new UsuariosDAO().cadastrarUsuarios(user);
                break;
            } else {
                System.out.println("O formato do cpf está errado ou o cpf já existe");
            }


        } while (true);



        System.out.println("---Conta Criada Com Sucesso---\nNOME COMPLETO: " + nome + "\nIDADE: " + user.getIdade());


    }

    public double resgatarSaldo() {
        return userDAO.resgatarSaldo(contaLogada);
    }

    public void logarConta() {
        if (contaLogada.isEmpty()) {
            System.out.println("CPF da conta: ");
            String cpfDigitado = sc.nextLine();

            if (cpfDigitado.equals(userDAO.getCpf(cpfDigitado))) {

                System.out.println("Senha da conta: ");
                String senhaDigitada = sc.nextLine();

                if (senhaDigitada.equals(userDAO.getSenha(cpfDigitado))) {
                    contaLogada = cpfDigitado;
                    System.out.printf("---Bem Vindo ao NATBANK---\n Sr(a). %s%n", userDAO.resgatarNome(cpfDigitado));

                } else {
                    System.out.println("Senha incorreta");
                }

            } else {
                System.out.println("CPF de conta não encontrado");
            }

        } else {
            System.out.println("Você já está logado em uma conta, saia primeiro para entrar em outra");
        }

    }

    public void depositar() {
        if (!contaLogada.isEmpty()) {
            do {
                try {
                    System.out.println("------------------");
                    System.out.println("Quanto você quer adicionar?: ");
                    double valor = Double.parseDouble(sc.nextLine());

                    if (valor > 0) {

                        double saldoResgatado = userDAO.resgatarSaldo(contaLogada);
                        double valorTotal = saldoResgatado + valor;
                        userDAO.alterarSaldo(contaLogada, valorTotal);
                        extrato.setCpf(contaLogada);
                        extrato.setAto("Depositado");
                        extrato.setDinheiro(valor);
                        new ExtratosDAO().inserirDados(extrato);
                        break;

                    } else {
                        System.out.println("Insira um valor positivo");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("APENAS NÚMEROS SÃO VÁLIDOS");
                }

            } while (true);
        } else {
            System.out.println("Entre em uma conta primeiro para realizar ações");
        }
    }

    public void sacar () {
        if (!contaLogada.isEmpty()) {
            double saldoResgatado = 0;

            do {
                try {
                    System.out.println("Quanto você deseja sacar?: ");
                    double valorSaque = Double.parseDouble(sc.nextLine());
                    saldoResgatado = userDAO.resgatarSaldo(contaLogada);
                    double total = saldoResgatado - valorSaque;

                    if (valorSaque > 0 && saldoResgatado > 0.0 && total > 0) {
                        userDAO.alterarSaldo(contaLogada, total);
                        System.out.println("---SAQUE REALIZADO---");
                        extrato.setCpf(contaLogada);
                        extrato.setAto("Sacado");
                        extrato.setDinheiro(valorSaque);
                        new ExtratosDAO().inserirDados(extrato);
                        break;

                    } else if (saldoResgatado == 0.0) {
                        System.out.println("Esse cpf não foi encontrado");
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("APENAS NÚMEROS SÃO VÁLIDOS");
                }
            } while (true);

        } else {
            System.out.println("Entre em uma conta primeiro para realizar ações");
        }
    }

    public void deletarConta(){
        if (!contaLogada.isEmpty()) {
        System.out.println("------------------");
        System.out.println("Digite o cpf da conta para excluir: ");
        String cpf = sc.nextLine();

        System.out.println("------------------");
        System.out.println("Digite sua senha");
        String senha = sc.nextLine();

        String senhaResgatada = userDAO.getSenha(cpf);

        if (cpf.length() == 11 && Objects.equals(senha, senhaResgatada)) {
            userDAO.deletarConta(cpf);
        } else if (cpf.length() != 11){
            System.out.println("------------------");
            System.out.println("Insira um formato válido de cpf");
        } else if (!Objects.equals(senha, senhaResgatada)) {
            System.out.println("------------------");
            System.out.println("Senha Incorreta ou CPF Incorreto");
        }
    } else {
            System.out.println("Entre em uma conta primeiro para realizar ações");
        }
    }

    public void transferirDinheiro() {
        if (!contaLogada.isEmpty()) {

            double valorTransferir = 0;
            System.out.println("------------------");
            System.out.println("Digite a senha da sua conta: ");
            String senha = sc.nextLine();
            String contaTerceiros = null;
            if (senha.equals(userDAO.getSenha(contaLogada))) {

                do {
                    try {
                        System.out.println("------------------");
                        System.out.println("Quanto Você Deseja Transferir? ");
                        valorTransferir = Double.parseDouble(sc.nextLine());


                        if (valorTransferir > 0 && valorTransferir <= userDAO.resgatarSaldo(contaLogada)) {


                                System.out.println("------------------");
                                System.out.println("Digite o cpf da conta para qual você deseja transferir: ");
                                contaTerceiros = sc.nextLine();

                                String seExisteCpf = userDAO.getCpf(contaTerceiros);


                                if (!seExisteCpf.isEmpty() && !seExisteCpf.equals(contaLogada)) {
                                    double saldoContaAtual = userDAO.resgatarSaldo(contaLogada); // Resgatando o SALDO da conta que deseja REALIZAR a transação
                                    double saldoContaTerceiro = userDAO.resgatarSaldo(contaTerceiros); // Resgatar o SALDO da conta que vai RECEBER a transação


                                    double subtrairSaldo = saldoContaAtual - valorTransferir; // Subtrai o SALDO da conta irá REALIZAR a transação
                                    double somarSaldo = saldoContaTerceiro + valorTransferir; // Soma o SALDO da conta que irá RECEBER a transação

                                    userDAO.alterarSaldo(contaLogada, subtrairSaldo);
                                    userDAO.alterarSaldo(contaTerceiros, somarSaldo);

                                    System.out.println("------------------");
                                    System.out.println("Transação Realizada");

                                    // Enviar registro para tabela Extratos Registrando a conta que transferiu
                                    extrato.setCpf(contaLogada);
                                    extrato.setAto("Transferido");
                                    extrato.setDinheiro(valorTransferir);
                                    extrato.setCpf_terceiro("Mandou");
                                    new ExtratosDAO().inserirDados(extrato);
                                    //

                                    // Enviar Registro para Tabela Extratos Registrando a conta que recebeu
                                    extrato.setCpf(contaTerceiros);
                                    extrato.setAto("Recebido");
                                    extrato.setDinheiro(valorTransferir);
                                    extrato.setCpf_terceiro(contaLogada);
                                    new ExtratosDAO().inserirDados(extrato);
                                    //

                                    break;

                                } else {
                                    System.out.println("CPF INVÁLIDO!");
                                }



                        } else if (valorTransferir > userDAO.resgatarSaldo(contaLogada)) {
                            System.out.println("SALDO INSUFICIENTE!");
                            break;
                        } else if (valorTransferir <= 0) {
                            System.out.println("Insira valores maiores do que 0");
                        }

                    } catch (java.lang.NumberFormatException e) {
                        System.out.println("Apenas números são válidos");
                    }
                } while (true);

            } else {
                System.out.println("SENHA INCORRETA!");
            }
        } else {
            System.out.println("Você já está logado em uma conta, saia primeiro para entrar em outra");
        }

    }

    public void deslogar(){
        System.out.println("Tem certeza que você quer sair?");
        String resposta = sc.nextLine();
        if (resposta.equalsIgnoreCase("sim")){
            System.out.println("--Saiu Da Conta--");
            contaLogada = "";
        } else {
            System.out.println("Continue Na Conta");
        }
    }

    public void extrato() {
        extratoDAO.extrato(contaLogada);

    }
}

