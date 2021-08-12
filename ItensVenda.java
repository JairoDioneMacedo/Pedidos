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
public class ItensVenda {

    private int itensCodigo;
    private int itensCodVenda;
    private int itensCodProd;
    private int itensQuant;
    private double itensTotalVenda;
    private String prodDescricao;
    private double prodValor;

    /**
     * @return the itensCodigo
     */
    public int getItensCodigo() {
        return itensCodigo;
    }

    /**
     * @param itensCodigo the itensCodigo to set
     */
    public void setItensCodigo(int itensCodigo) {
        this.itensCodigo = itensCodigo;
    }

    /**
     * @return the itensCodVenda
     */
    public int getItensCodVenda() {
        return itensCodVenda;
    }

    /**
     * @param itensCodVenda the itensCodVenda to set
     */
    public void setItensCodVenda(int itensCodVenda) {
        this.itensCodVenda = itensCodVenda;
    }

    /**
     * @return the itensCodProd
     */
    public int getItensCodProd() {
        return itensCodProd;
    }

    /**
     * @param itensCodProd the itensCodProd to set
     */
    public void setItensCodProd(int itensCodProd) {
        this.itensCodProd = itensCodProd;
    }

    /**
     * @return the itensQuant
     */
    public int getItensQuant() {
        return itensQuant;
    }

    /**
     * @param itensQuant the itensQuant to set
     */
    public void setItensQuant(int itensQuant) {
        this.itensQuant = itensQuant;
    }

    /**
     * @return the itensTotalVenda
     */
    public double getItensTotalVenda() {
        return itensTotalVenda;
    }

    /**
     * @param itensTotalVenda the itensTotalVenda to set
     */
    public void setItensTotalVenda(double itensTotalVenda) {
        this.itensTotalVenda = itensTotalVenda;
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
