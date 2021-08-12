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
public class ItensRevenda {

    private int itensCodRevenda;
    private int codRevenda;
    private int itensCodProd;
    private int itensQuantRevenda;
    private double itensTotalRevenda;
    private String prodDescricao;
    private double prodValor;

    /**
     * @return the itensCodRevenda
     */
    public int getItensCodRevenda() {
        return itensCodRevenda;
    }

    /**
     * @param itensCodRevenda the itensCodRevenda to set
     */
    public void setItensCodRevenda(int itensCodRevenda) {
        this.itensCodRevenda = itensCodRevenda;
    }

    /**
     * @return the codRevenda
     */
    public int getCodRevenda() {
        return codRevenda;
    }

    /**
     * @param codRevenda the codRevenda to set
     */
    public void setCodRevenda(int codRevenda) {
        this.codRevenda = codRevenda;
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
     * @return the itensQuantRevenda
     */
    public int getItensQuantRevenda() {
        return itensQuantRevenda;
    }

    /**
     * @param itensQuantRevenda the itensQuantRevenda to set
     */
    public void setItensQuantRevenda(int itensQuantRevenda) {
        this.itensQuantRevenda = itensQuantRevenda;
    }

    /**
     * @return the itensTotalRevenda
     */
    public double getItensTotalRevenda() {
        return itensTotalRevenda;
    }

    /**
     * @param itensTotalRevenda the itensTotalRevenda to set
     */
    public void setItensTotalRevenda(double itensTotalRevenda) {
        this.itensTotalRevenda = itensTotalRevenda;
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
