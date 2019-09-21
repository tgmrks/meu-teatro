package br.fatec.meuteatro.beans;

/**
 * Created by ismael on 14/05/15.
 */
public class T04Bean {

    private int _id, id_t;
    private String txtTeatro, txtCidade, txtUF, txtEndereco;

    public T04Bean(){}

    public T04Bean(int _id, String txtTeatro, String txtCidade, String txtUF, String txtEndereco, int id_t){

        this._id = _id;
        this.txtTeatro = txtTeatro;
        this.txtCidade = txtCidade;
        this.txtUF = txtUF;
        this.txtEndereco = txtEndereco;
        this.id_t = id_t;
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

    public String getTxtTeatro() {
        return txtTeatro;
    }

    public void setTxtTeatro(String txtTeatro) {
        this.txtTeatro = txtTeatro;
    }

    public String getTxtCidade() {
        return txtCidade;
    }

    public void setTxtCidade(String txtCidade) {
        this.txtCidade = txtCidade;
    }

    public String getTxtUF() {
        return txtUF;
    }

    public void setTxtUF(String txtUF) {
        this.txtUF = txtUF;
    }

    public String getTxtEndereco() {
        return txtEndereco;
    }

    public void setTxtEndereco(String txtEndereco) {
        this.txtEndereco = txtEndereco;
    }

}
