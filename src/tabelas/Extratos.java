package tabelas;

public class Extratos {
    protected String cpf, cpf_terceiro;
    protected String ato;
    protected double dinheiro;

    public String getCpf_terceiro() {
        return cpf_terceiro;
    }

    public void setCpf_terceiro(String cpf_terceiro) {
        this.cpf_terceiro = cpf_terceiro;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAto() {
        return ato;
    }

    public void setAto(String ato) {
        this.ato = ato;
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(double dinheiro) {
        this.dinheiro = dinheiro;
    }
}