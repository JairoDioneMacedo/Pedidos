/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.modelo;

/**
 *
 * @author Jairo
 */
public class Produtos {

    private int prodCodigo;
    private String prodDescricao;
    private double prodValor;

    /**
     * @return the prodCodigo
     */
    public int getProdCodigo() {
        return prodCodigo;
    }

    /**
     * @param prodCodigo the prodCodigo to set
     */
    public void setProdCodigo(int prodCodigo) {
        this.prodCodigo = prodCodigo;
    }

    /**
     * @return the prodDescricao
     */
    public String getProdDescricao() {
        return prodDescricao;
    }

    /**
     * @param prodDescricao the prodDescricao to set
     */
    public void setProdDescricao(String prodDescricao) {
        this.prodDescricao = prodDescricao;
    }

    /**
     * @return the prodValor
     */
    public double getProdValor() {
        return prodValor;
    }

    /**
     * @param prodValor the prodValor to set
     */
    public void setProdValor(double prodValor) {
        this.prodValor = prodValor;
    }
}
