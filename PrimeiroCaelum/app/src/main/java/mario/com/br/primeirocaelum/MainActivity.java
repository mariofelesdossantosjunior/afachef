package mario.com.br.primeirocaelum;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mario.com.br.primeirocaelum.dao.AlunosDao;
import mario.com.br.primeirocaelum.model.Alunos;


public class MainActivity extends Activity {
    ListView  listaCadastro;
     private Alunos alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaCadastro = (ListView) findViewById(R.id.lista);
        registerForContextMenu(listaCadastro);
        caregaLista();

        listaCadastro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                alunos = (Alunos) adapterView.getItemAtPosition(posicao);
                Log.i("mario","ALUNO RECUPERADO: "+alunos);
                //Toast.makeText(MainActivity.this,"POSIÇÂO DO ITEM É "+posicao,Toast.LENGTH_SHORT).show();
            }
        });
        listaCadastro.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                alunos = (Alunos) adapterView.getItemAtPosition(posicao);
                //Toast.makeText(MainActivity.this,"NOME DO ITEM É "+adapterView.getItemAtPosition(posicao),Toast.LENGTH_LONG).show();
                //Outra forma de pegar item selecionado
                // Log.i("mario","NOME ITEM "+ adapterView.getItemAtPosition(posicao));
                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem del = menu.add("Deletar");
        del.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlunosDao alunosDao = new AlunosDao(MainActivity.this);
                Log.i("teste", "ALUNO SELECIONADO: " + alunos.toString());
                alunosDao.deletar(alunos);
                alunosDao.close();
                caregaLista();
                return false;
            }
        });
        MenuItem edit = menu.add("Editar Aluno");
        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this,Formulario.class);
                intent.putExtra("alunoedit",alunos);
                Log.i("mario","Aluno Sel: "+alunos.toString());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        caregaLista();
    }

    public void caregaLista(){
        AlunosDao alunosDao = new AlunosDao(this);
        List<Alunos> alunos = alunosDao.getLista();
        final ClienteAdapter adapter = new ClienteAdapter(this,alunos);
        listaCadastro.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.novo:
                Intent formulario = new Intent(this, Formulario.class);
                startActivity(formulario);
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}

class ClienteAdapter extends BaseAdapter {
    private Context context;
    private List<Alunos> lista;

    public ClienteAdapter(Context context, List<Alunos> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int posicao, View convertView, ViewGroup parent) {
        final Alunos alunos = lista.get(posicao);
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_lista_cadastro, null);
        TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        tvNome.setText(alunos.getNome().toString());
        return view;
    }
}


