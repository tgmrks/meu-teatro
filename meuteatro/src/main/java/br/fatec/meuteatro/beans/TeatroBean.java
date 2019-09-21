package br.fatec.meuteatro.beans;

/**
 * Created by ismael on 15/04/15.
 */
public class TeatroBean {

    private int id_t;
    private String nome_teatro;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String uf;
    //private String cep;
    private String tel;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

//    public String getCep() {
//        return cep;
//    }
//
//    public void setCep(String cep) {
//        this.cep = cep;
//    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId_t() {
        return id_t;
    }

    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    public String getNome_teatro() {
        return nome_teatro;
    }

    public void setNome_teatro(String nome_teatro) {
        this.nome_teatro = nome_teatro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
