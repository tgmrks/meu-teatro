package br.fatec.meuteatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.fatec.meuteatro.banco.MeusTeatrosDAO;
import br.fatec.meuteatro.beans.MeusTeatrosBean;
import br.fatec.meuteatro.beans.T04Bean;

/**
 * Created by ismael on 14/05/15.
 */
public class T04Adapter extends BaseAdapter {

    private Context contexto;
    private List<T04Bean> t04Beans;
    private MeusTeatrosDAO mtDao;
    private MeusTeatrosBean mtBean;

    public T04Adapter(Context contexto, List<T04Bean> t04Beans) {

        this.contexto = contexto;
        this.t04Beans = t04Beans;
            }

    @Override
    public int getCount() {
        return t04Beans.size();
    }

    @Override
    public Object getItem(int i) {
        return t04Beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {

        final View rootView = LayoutInflater.from(contexto).inflate(R.layout.t04_item, viewGroup, false);

        TextView txtTeatro = (TextView) rootView.findViewById(R.id.txtViewMeusTeatrosTeatro);
        TextView txtCidade = (TextView) rootView.findViewById(R.id.txtMeusTeatrosCidade);
        TextView txtUF = (TextView) rootView.findViewById(R.id.txtMeusTeatrosUF);
        TextView txtEndereco = (TextView) rootView.findViewById(R.id.txtMeusTeatrosEnd);

        final T04Bean beanDaVez = t04Beans.get(position);

        txtTeatro.setText(beanDaVez.getTxtTeatro());
        txtCidade.setText(" - " + beanDaVez.getTxtCidade() + " ");
        txtUF.setText("(" + beanDaVez.getTxtUF() + ")");
        txtEndereco.setText(beanDaVez.getTxtEndereco());

        Button btnRmv = (Button)rootView.findViewById(R.id.btnRemoverTeatro);
        btnRmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mtBean = new MeusTeatrosBean();
                mtBean.set_id(beanDaVez.get_id());
                mtBean.setNome_teatro(beanDaVez.getTxtTeatro());
                mtBean.setCidade(beanDaVez.getTxtCidade());
                mtBean.setUf(beanDaVez.getTxtUF());
                mtBean.setEndereco(beanDaVez.getTxtEndereco());
                mtBean.setId_t(beanDaVez.getId_t());

                mtDao = new MeusTeatrosDAO(contexto.getApplicationContext());
                mtDao.open();
                mtDao.deletar(mtBean);
                mtDao.close();

                Toast toast = Toast.makeText(contexto.getApplicationContext(), "Teatro removido", Toast.LENGTH_SHORT);toast.show();

                //remove from ArrayAdapter type T04Bean in the clicked possition
                t04Beans.remove(position);
                notifyDataSetChanged();
            }
        });

        return rootView;
    }
}
