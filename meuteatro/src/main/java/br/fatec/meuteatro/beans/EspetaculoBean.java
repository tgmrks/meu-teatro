package br.fatec.meuteatro.beans;

/**
 * Created by ismael on 15/04/15.
 */
public class EspetaculoBean {

//    private int id_usuario;
    private int id_t;
    private int id_e;
    private String titulo;
    private String descricao;
    private String classificacao;
    private String data_hora; ///verificar o funciomento de data e hora
    private byte[] imagem;
    private String entrada;
    private String link_externo;
    //private String data_cadastrada;
    //private int ativo;


    public int getId_e() {
        return id_e;
    }
    public void setId_e(int id_e) {
        this.id_e = id_e;
    }
//    public int getId_usuario() {
//        return id_usuario;
//    }
//    public void setId_usuario(int id_usuario) {
//        this.id_usuario = id_usuario;
//    }
//
//    public String getData_cadastrada() {
//        return data_cadastrada;
//    }
//    public void setData_cadastrada(String data_cadastrada) {
//        this.data_cadastrada = data_cadastrada;
//    }
//    public int getAtivo() {
//        return ativo;
//    }
//    public void setAtivo(int ativo) {
//        this.ativo = ativo;
//    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getData_hora() {
        return data_hora;
    }
    public void setData_hora(String data_hora) {
        this.data_hora = data_hora;
    }
    public String getClassificacao() {
        return classificacao;
    }
    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }
    public byte[] getImagem() {
        return imagem;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    public String getEntrada() {
        return entrada;
    }
    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }
    public String getLink_externo() {
        return link_externo;
    }
    public void setLink_externo(String link_externo) {
        this.link_externo = link_externo;
    }
    public int getId_t() {
        return id_t;
    }
    public void setId_t(int id_t) {
        this.id_t = id_t;
    }
}
