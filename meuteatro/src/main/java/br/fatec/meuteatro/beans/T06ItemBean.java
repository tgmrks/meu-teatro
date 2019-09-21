package br.fatec.meuteatro.beans;

/**
 * Created by ismael on 23/11/15.
 */
public class T06ItemBean {
    private byte[] img;
    private String txtTitulo, txtEntrada, txtClassificacao, txtDataHora;
    private int id_e;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getTxtTitulo() {
        return txtTitulo;
    }

    public void setTxtTitulo(String txtTitulo) {
        this.txtTitulo = txtTitulo;
    }

    public String getTxtEntrada() {
        return txtEntrada;
    }

    public void setTxtEntrada(String txtEntrada) {
        this.txtEntrada = txtEntrada;
    }

    public String getTxtClassificacao() {
        return txtClassificacao;
    }

    public void setTxtClassificacao(String txtClassificacao) {
        this.txtClassificacao = txtClassificacao;
    }

    public String getTxtDataHora() {
        return txtDataHora;
    }

    public void setTxtDataHora(String txtDataHora) {
        this.txtDataHora = txtDataHora;
    }

    public int getId_e() {
        return id_e;
    }

    public void setId_e(int id_e) {
        this.id_e = id_e;
    }
}
