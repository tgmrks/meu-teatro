package br.fatec.meuteatro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.beans.T06HeaderBean;
import br.fatec.meuteatro.beans.T06ItemBean;

/**
 * Created by ismael on 16/03/15.
 */
public class T06Adapter extends BaseAdapter {

    private List<Object> listObj = new ArrayList<Object>();
    private Context contexto;

    public T06Adapter(Context contexto, List<Object> listObj) {
        this.listObj = listObj;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listObj.size();
    }
    @Override
    public Object getItem(int arg0) {
        return listObj.get(arg0);
    }
    @Override
    public long getItemId(int arg0) {
        if(listObj.get(arg0) instanceof T06HeaderBean){
            return ((T06HeaderBean) listObj.get(arg0)).getId_t();
        }
        else{
            return ((T06ItemBean) listObj.get(arg0)).getId_e();
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView;
        if (listObj.get(position) instanceof T06HeaderBean) {
            //CARREGA HEADER
            //System.out.println("INSTANCEOF HEADER");
            rootView = LayoutInflater.from(contexto).inflate(R.layout.t06_header, parent, false);
            TextView txtTeatro = (TextView) rootView.findViewById(R.id.txtViewItemTeatro);
            TextView txtCidade = (TextView) rootView.findViewById(R.id.txtViewItemCidade);
            TextView txtUf = (TextView) rootView.findViewById(R.id.txtViewItemUf);

            T06HeaderBean header = (T06HeaderBean) listObj.get(position);
            txtTeatro.setText(header.getTxtTeatro());
            txtCidade.setText(" - " + header.getTxtCidade());
            txtUf.setText("(" + header.getTxtUf() + ")");
        } else {
            //CARREGA ITENS
            //System.out.println("INSTANCEOF ITEM");
            rootView = LayoutInflater.from(contexto).inflate(R.layout.t06_item, parent, false);

            ImageView image = (ImageView) rootView.findViewById(R.id.imgViewItem);
            ImageView imgClassificacao = (ImageView) rootView.findViewById(R.id.imageViewClassificacao1);
            TextView txtTitulo = (TextView) rootView.findViewById(R.id.txtViewItemTitulo);
            TextView txtDataHora = (TextView) rootView.findViewById(R.id.txtViewDataHora);

            T06ItemBean item = (T06ItemBean) listObj.get(position);
            rootView.setId(item.getId_e());

            txtTitulo.setText(item.getTxtTitulo());
            txtDataHora.setText(item.getTxtDataHora());

            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImg(), 0, item.getImg().length);
            image.setImageBitmap(bitmap);

            int cl = 0;
            cl = Integer.parseInt(item.getTxtClassificacao());
            switch (cl) {
                case 0:
                    imgClassificacao.setImageResource(R.drawable.c_0);
                    break;
                case 10:
                    imgClassificacao.setImageResource(R.drawable.c_10);
                    break;
                case 12:
                    imgClassificacao.setImageResource(R.drawable.c_12);
                    break;
                case 14:
                    imgClassificacao.setImageResource(R.drawable.c_14);
                    break;
                case 16:
                    imgClassificacao.setImageResource(R.drawable.c_16);
                    break;
                case 18:
                    imgClassificacao.setImageResource(R.drawable.c_18);
                    break;
            }
        }
        return rootView;
    }
}