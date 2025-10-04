package tabelas;

public class Emprestimos {
    protected String cpfUsuario;
    protected float valorEmprestado, montanteFinal, parcelas, valorParcelas, valorRestante;

    public float getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(float valorRestante) {
        this.valorRestante = valorRestante;
    }

    public float getValorParcelas() {
        return valorParcelas;
    }

    public void setValorParcelas(float valorParcelas) {
        this.valorParcelas = valorParcelas;
    }

    public float getParcelas() {
        return parcelas;
    }

    public void setParcelas(float parcelas) {
        this.parcelas = parcelas;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public float getValorEmprestado() {
        return valorEmprestado;
    }

    public void setValorEmprestado(float valorEmprestado) {
        this.valorEmprestado = valorEmprestado;
    }

    public float getMontanteFinal() {
        return montanteFinal;
    }

    public void setMontanteFinal(float montanteFinal) {
        this.montanteFinal = montanteFinal;
    }
}
