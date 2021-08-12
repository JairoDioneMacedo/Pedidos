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
public class Venda {

    private int venCodigo;
    private Date venData;
    private String venCli;
    private double venValTotal;
    private String venObs;
    private boolean venSituacao;

    /**
     * @return the venCodigo
     */
    public int getVenCodigo() {
        return venCodigo;
    }

    /**
     * @param venCodigo the venCodigo to set
     */
    public void setVenCodigo(int venCodigo) {
        this.venCodigo = venCodigo;
    }

    /**
     * @return the venData
     */
    public Date getVenData() {
        return venData;
    }

    /**
     * @param venData the venData to set
     */
    public void setVenData(Date venData) {
        this.venData = venData;
    }

    /**
     * @return the venCli
     */
    public String getVenCli() {
        return venCli;
    }

    /**
     * @param venCli the venCli to set
     */
    public void setVenCli(String venCli) {
        this.venCli = venCli;
    }

    /**
     * @return the venValTotal
     */
    public double getVenValTotal() {
        return venValTotal;
    }

    /**
     * @param venValTotal the venValTotal to set
     */
    public void setVenValTotal(double venValTotal) {
        this.venValTotal = venValTotal;
    }

    /**
     * @return the venObs
     */
    public String getVenObs() {
        return venObs;
    }

    /**
     * @param venObs the venObs to set
     */
    public void setVenObs(String venObs) {
        this.venObs = venObs;
    }

    /**
     * @return the venSituacao
     */
    public boolean getVenSituacao() {
        return venSituacao;
    }

    /**
     * @param venSituacao the venSituacao to set
     */
    public void setVenSituacao(boolean venSituacao) {
        this.venSituacao = venSituacao;
    }
}
