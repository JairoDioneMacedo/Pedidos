/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.modelo;

import java.util.Date;

/**
 *
 * @author Jairo
 */
public class Revenda {

    private int revenCodigo;
    private Date revenData;
    private String revenCli;
    private double revenValorTotal;
    private String revenObs;
    private boolean revenSituacao;

    /**
     * @return the revenCodigo
     */
    public int getRevenCodigo() {
        return revenCodigo;
    }

    /**
     * @param revenCodigo the revenCodigo to set
     */
    public void setRevenCodigo(int revenCodigo) {
        this.revenCodigo = revenCodigo;
    }

    /**
     * @return the revenData
     */
    public Date getRevenData() {
        return revenData;
    }

    /**
     * @param revenData the revenData to set
     */
    public void setRevenData(Date revenData) {
        this.revenData = revenData;
    }

    /**
     * @return the revenCli
     */
    public String getRevenCli() {
        return revenCli;
    }

    /**
     * @param revenCli the revenCli to set
     */
    public void setRevenCli(String revenCli) {
        this.revenCli = revenCli;
    }

    /**
     * @return the revenValorTotal
     */
    public double getRevenValorTotal() {
        return revenValorTotal;
    }

    /**
     * @param revenValorTotal the revenValorTotal to set
     */
    public void setRevenValorTotal(double revenValorTotal) {
        this.revenValorTotal = revenValorTotal;
    }

    /**
     * @return the revenObs
     */
    public String getRevenObs() {
        return revenObs;
    }

    /**
     * @param revenObs the revenObs to set
     */
    public void setRevenObs(String revenObs) {
        this.revenObs = revenObs;
    }

    /**
     * @return the revenSituacao
     */
    public boolean isRevenSituacao() {
        return revenSituacao;
    }

    /**
     * @param revenSituacao the revenSituacao to set
     */
    public void setRevenSituacao(boolean revenSituacao) {
        this.revenSituacao = revenSituacao;
    }
}
