package br.fatec.meuteatro.beans;

/**
 * Created by ismael on 29/04/15.
 */
public class MeusTeatrosBean {

    //    "CREATE TABLE meus_teatros (_id INTEGER PRIMARY KEY, " +
//            "id_t INTEGER, nome_teatro TEXT, cidade TEXT)";

    private int _id;
    private String nome_teatro;
    private String cidade;
    private String uf;
    private String endereco;
    private int id_t;
    private int checked;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
