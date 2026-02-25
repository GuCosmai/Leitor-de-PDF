package models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Apolice {
    private String numero;
    private String tipo; // APÓLICE, ENDOSSO, PROPOSTA
    private String seguradora;
    private Segurado segurado;
    private LocalDate dataVigenciaInicio;
    private LocalDate dataVigenciaFim;
    private Double premio;
    private String moeda;
    private Map<String, String> dadosAdicionais;

    public Apolice() {
        this.dadosAdicionais = new HashMap<>();
    }

    public Apolice(String numero, String tipo, String seguradora) {
        this();
        this.numero = numero;
        this.tipo = tipo;
        this.seguradora = seguradora;
    }

    // Getters e Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    public Segurado getSegurado() {
        return segurado;
    }

    public void setSegurado(Segurado segurado) {
        this.segurado = segurado;
    }

    public LocalDate getDataVigenciaInicio() {
        return dataVigenciaInicio;
    }

    public void setDataVigenciaInicio(LocalDate dataVigenciaInicio) {
        this.dataVigenciaInicio = dataVigenciaInicio;
    }

    public LocalDate getDataVigenciaFim() {
        return dataVigenciaFim;
    }

    public void setDataVigenciaFim(LocalDate dataVigenciaFim) {
        this.dataVigenciaFim = dataVigenciaFim;
    }

    public Double getPremio() {
        return premio;
    }

    public void setPremio(Double premio) {
        this.premio = premio;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public Map<String, String> getDadosAdicionais() {
        return dadosAdicionais;
    }

    public void setDadosAdicionais(Map<String, String> dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    public void adicionarDado(String chave, String valor) {
        this.dadosAdicionais.put(chave, valor);
    }

    public String obterDado(String chave) {
        return this.dadosAdicionais.get(chave);
    }

    @Override
    public String toString() {
        return "Apolice{" +
                "numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", seguradora='" + seguradora + '\'' +
                ", dataVigenciaInicio=" + dataVigenciaInicio +
                ", dataVigenciaFim=" + dataVigenciaFim +
                ", premio=" + premio +
                ", moeda='" + moeda + '\'' +
                ", segurado=" + segurado +
                ", dadosAdicionais=" + dadosAdicionais +
                '}';
    }
}
