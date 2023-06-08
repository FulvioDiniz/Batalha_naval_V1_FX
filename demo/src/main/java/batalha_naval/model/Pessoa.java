package batalha_naval.model;

public class Pessoa {
    private String nome;
    private int ponto;
    private int acertos;
    private int erros;

    public Pessoa() {
    }

    public Pessoa(String nome, int ponto, int acertos, int erros) {
        this.nome = nome;
        this.ponto = ponto;
        this.acertos = acertos;
        this.erros = erros;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPonto() {
        return ponto;
    }

    public void setPonto(int ponto) {
        this.ponto = ponto;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

}
