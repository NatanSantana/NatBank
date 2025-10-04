package Code;
import DAO.EmprestimosDAO;
import DAO.ExtratosDAO;
import DAO.UsuariosDAO;
import tabelas.Emprestimos;
import tabelas.Extratos;
import tabelas.Usuarios;

import java.util.Objects;
import java.util.Scanner;

public class Banco {



    protected String contaLogada = "";
    protected final double juros = 0.05;
    private final Usuarios user = new Usuarios();
    private final UsuariosDAO userDAO = new UsuariosDAO();
    private final ExtratosDAO extratoDAO = new ExtratosDAO();
    private final Extratos extrato = new Extratos();
    private final Emprestimos empres = new Emprestimos();
    private final EmprestimosDAO empresDAO = new EmprestimosDAO();
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



            String cpfExiste = userDAO.resgatarCpf(cpf);

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

            if (cpfDigitado.equals(userDAO.resgatarCpf(cpfDigitado))) {

                System.out.println("Senha da conta: ");
                String senhaDigitada = sc.nextLine();

                if (senhaDigitada.equals(userDAO.resgatarSenha(cpfDigitado))) {
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
                        System.out.println("Insira um valorSolicitado positivo");
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
            double saldoResgatado;

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

        String senhaResgatada = userDAO.resgatarSenha(cpf);

        if (cpf.length() == 11 && Objects.equals(senha, senhaResgatada)) {
            userDAO.deletarConta(cpf);
        } else if (cpf.length() != 11){
            System.out.println("------------------");
            System.out.println("Insira um formato válido de cpf");
        } else {
            System.out.println("------------------");
            System.out.println("Senha Incorreta ou CPF Incorreto");
        }
    } else {
            System.out.println("Entre em uma conta primeiro para realizar ações");
        }
    }

    public void transferirDinheiro() {
        if (!contaLogada.isEmpty()) {

            double valorTransferir;
            System.out.println("------------------");
            System.out.println("Digite a senha da sua conta: ");
            String senha = sc.nextLine();
            String contaTerceiros;
            if (senha.equals(userDAO.resgatarSenha(contaLogada))) {

                do {
                    try {
                        System.out.println("------------------");
                        System.out.println("Quanto Você Deseja Transferir? ");
                        valorTransferir = Double.parseDouble(sc.nextLine());


                        if (valorTransferir > 0 && valorTransferir <= userDAO.resgatarSaldo(contaLogada)) {


                                System.out.println("------------------");
                                System.out.println("Digite o cpf da conta para qual você deseja transferir: ");
                                contaTerceiros = sc.nextLine();

                                String seExisteCpf = userDAO.resgatarCpf(contaTerceiros);


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

    public void solicitarEmprestimo() {

        Scanner sc = new Scanner(System.in);

        if (!contaLogada.isEmpty()) {
            do {
                try {
                    System.out.println("Qual o Valor do Empréstimo? (VALOR MÁXIMO: 100.000)");
                    float valorSolicitado = (Integer.parseInt(sc.nextLine()));

                    if (valorSolicitado <= 0 || valorSolicitado > 100000) {
                        System.out.println("É VÁLIDO APENAS VALORES MAIORES DO QUE 100 E MENORES QUE 100.000");

                    } else {
                        System.out.println("Deseja Parcelar em Quantos meses?\n3x\n6x\n12x");
                        int meses = (Integer.parseInt(sc.nextLine()));

                        if (meses == 3 || meses == 6 || meses == 12) {

                            float jurosSomado = (float) (1 + juros);
                            float potenciacaoNegativa = (float) Math.pow(jurosSomado, -meses);
                            float divisor = 1 - (potenciacaoNegativa);
                            float pmt = (float) ((valorSolicitado * juros) / divisor);
                            float montanteFinal = pmt * meses;

                            System.out.printf("Valor do Empréstimo: R$%.2f%n", valorSolicitado);
                            int porcentagem = (int) (juros * 100);
                            System.out.printf("Juros: %s%%\n", porcentagem);
                            System.out.printf("Parcelas: R$%.2f%n", pmt);
                            System.out.printf("Meses: %s%n", meses);
                            System.out.printf("Total a Pagar: R$%.2f%n", montanteFinal);

                            System.out.println("CONFIRME O EMPRÉSTIMO:\nSIM OU NÃO?");
                            String confirmacao = sc.nextLine();

                            if (confirmacao.equalsIgnoreCase("sim")) {
                                System.out.println("---EMPRÉSTIMO REALIZADO---");
                                userDAO.alterarSaldo(contaLogada, resgatarSaldo() + valorSolicitado);
                                empres.setCpfUsuario(contaLogada);
                                empres.setValorEmprestado(valorSolicitado);
                                empres.setMontanteFinal(montanteFinal);
                                empres.setParcelas(meses);
                                empres.setValorParcelas(pmt);
                                empres.setValorRestante(montanteFinal);
                                empresDAO.cadastrarEmprestimos(empres);

                                extratoDAO.setCpf(contaLogada);
                                extratoDAO.setAto("Emprestado");
                                extratoDAO.setDinheiro(valorSolicitado);
                                extratoDAO.inserirDados(extratoDAO);

                            } else {
                                System.out.println("---Empréstimo Cancelado---");
                            }
                            break;
                        } else {
                            System.out.println("Selecione uma opção válida de parcelas");
                        }
                    }
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Insira apenas números");
                }
            } while (true);

            } else {
            System.out.println("Entre em uma conta primeiramente!");
        }
    }

    public void pagarEmprestimo(){
        if (!contaLogada.isEmpty()) {
            do {
                if (empresDAO.resgatarNumeroParcelas(contaLogada) != 0) {
                    System.out.println("---PARCELAS---");
                    for (int i = 1; i <= empresDAO.resgatarNumeroParcelas(contaLogada); i++) {
                        System.out.printf(i + "º Parcela: R$%.2f%n", empresDAO.resgatarValorParcela(contaLogada));
                    }
                    System.out.println("Deseja Realizar Pagamento? (sim ou não)");
                    String confirmacao = sc.nextLine();

                    if (confirmacao.equalsIgnoreCase("sim")) {
                        System.out.println("Deseja Pagar Quantas Parcelas?");
                        int quantasParcelas = Integer.parseInt(sc.nextLine());

                        if (quantasParcelas <= empresDAO.resgatarNumeroParcelas(contaLogada)) {

                            float valorPagar = empresDAO.resgatarValorParcela(contaLogada) * quantasParcelas;

                            System.out.printf("Total: " + "R$%.2f%n", valorPagar);

                            System.out.println("Confirmar Pagamento?");
                            String confirmarPagamento = sc.nextLine();

                            if (confirmarPagamento.equalsIgnoreCase("sim") && resgatarSaldo() >= valorPagar) {
                                double saldoSubtraido = resgatarSaldo() - valorPagar;
                                userDAO.alterarSaldo(contaLogada, saldoSubtraido);
                                System.out.println("---Pagamento Realizado---");

                                extrato.setCpf(contaLogada);
                                extrato.setAto("Parcelas Pagas: " + quantasParcelas);
                                extrato.setDinheiro(valorPagar);
                                extratoDAO.inserirDados(extrato);

                                int qtdeParcelas = empresDAO.resgatarNumeroParcelas(contaLogada) - quantasParcelas;
                                double valorRestante = empresDAO.resgatarValorRestante(contaLogada) - valorPagar;


                                empresDAO.alterarNumeroParcelas(contaLogada, qtdeParcelas);
                                empresDAO.alterarvalorRestante(contaLogada, valorRestante);
                                System.out.println(qtdeParcelas + " Parcelas Restatante(s)");

                                if (empresDAO.resgatarNumeroParcelas(contaLogada) == 0) {
                                    System.out.println("Empréstimo Quitado Com Sucesso");
                                    empresDAO.deletarEmprestimo(contaLogada);

                                    extrato.setCpf(contaLogada);
                                    extrato.setAto("Empréstimo Quitado");
                                    extrato.setDinheiro(extratoDAO.valorEmprestado(contaLogada, "Emprestado"));
                                    extratoDAO.inserirDados(extrato);
                                }

                            } else {
                                System.out.println("Operação Cancelada");
                            }
                            break;

                        } else {
                            System.out.println("O número de parcelas inserido deve ser menor ou igual ao número de parcelas existentes");
                        }
                    } else {
                        System.out.println("Insira um valor válido (sim ou não)");
                    }

                } else {
                    System.out.println("Você Não Tem Um Empréstimo Ativo Para Pagar Parcelas");
                    break;
                }
            } while (true);

        }
    }
}
