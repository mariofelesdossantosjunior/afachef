package mario.com.br.primeirocaelum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import mario.com.br.primeirocaelum.dao.AlunosDao;
import mario.com.br.primeirocaelum.model.Alunos;

/**
 * Created by mario on 12/11/2014.
 */
public class Formulario extends Activity {

    private FormularioHelp helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        helper = new FormularioHelp(this);

        /**
         * Recuperando  o aluno selecionado na lista
         */
        Intent it = getIntent();
        final Alunos alEditar = (Alunos) it.getSerializableExtra("alunoedit");


        Button btSalvar = (Button) findViewById(R.id.btSalvar);
        //Verifica se esta alterando aluno
        if(alEditar != null){
            btSalvar.setText("Alterar");
            helper.recuperaAlunoForm(alEditar);
        }
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alunos alunos = helper.buscaAlunoFormulario();
                AlunosDao dao = new AlunosDao(Formulario.this);

                //Verifica se não esta sendo editado
                if(alEditar == null){
                    dao.salva(alunos);
                }else{
                    alunos.setId(alEditar.getId());
                    dao.alterarAluno(alunos);
                }
                dao.close();
                finish();
            }
        });

    }
}
