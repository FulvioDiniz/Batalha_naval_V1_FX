package batalha_naval.model;

import java.sql.Date;

public class Barco {
    private int ponto;
    private int tamanho;
    private int acertos;
    private int erros;
    private Date tempo;
    private String nome;

    public Barco() {
    }

    public Barco(int ponto, int tamanho, int acertos, int erros, Date tempo, String nome) {
        this.ponto = ponto;
        this.tamanho = tamanho;
        this.acertos = acertos;
        this.erros = erros;
        this.tempo = tempo;
        this.nome = nome;
    }

    public int getPonto() {
        return ponto;
    }

    public void setPonto(int ponto) {
        this.ponto = ponto;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
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

    public Date getTempo() {
        return tempo;
    }

    public void setTempo(Date tempo) {
        this.tempo = tempo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
