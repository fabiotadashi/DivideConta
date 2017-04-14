package br.com.fabiomiyasato.divideconta.model;

/**
 * Created by FabioMiyasato on 01/04/17.
 */

public class Item {

    private int id;
    private String descricao;
    private double valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
