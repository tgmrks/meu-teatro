package br.fatec.meuteatro.beans;

/**
 * Created by ismael on 19/10/15.
 */
public class AgendaBean {

    private int id_a;
    private int id_e;
    private int dia;
    private int mes;
    private int ano;
    private String hora;

    public int getId_a() {
        return id_a;
    }
    public void setId_a(int id_a) {
        this.id_a = id_a;
    }
    public int getId_e() {
        return id_e;
    }
    public void setId_e(int id_e) {
        this.id_e = id_e;
    }
    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }
    public int getMes() {
        return mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
}
