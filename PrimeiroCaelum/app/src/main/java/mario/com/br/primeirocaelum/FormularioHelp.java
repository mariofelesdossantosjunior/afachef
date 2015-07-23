package mario.com.br.primeirocaelum;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import mario.com.br.primeirocaelum.model.Alunos;

/**
 * Created by mario on 13/11/2014.
 */
public class FormularioHelp {
    private EditText etNome;
    private EditText etSite;
    private EditText etEndereco;
    private EditText etTelefone;
    private RatingBar rtNota;
    private Button btSalvar;
    private ImageView imagem;

    public FormularioHelp(Formulario formulario) {

        etNome = (EditText) formulario.findViewById(R.id.etNome);
        etSite =  (EditText) formulario.findViewById(R.id.etSite);
        etEndereco = (EditText) formulario.findViewById(R.id.etEndereco);
        etTelefone = (EditText) formulario.findViewById(R.id.etTelefone);
        rtNota = (RatingBar) formulario.findViewById(R.id.rtNota);
        btSalvar = (Button) formulario.findViewById(R.id.btSalvar);
        imagem = (ImageView) formulario.findViewById(R.id.imageView);
    }

    public Alunos buscaAlunoFormulario() {
        Alunos aluno = new Alunos();
        aluno.setNome(etNome.getText().toString());
        aluno.setSite(etSite.getText().toString());
        aluno.setEndereco(etEndereco.getText().toString());
        aluno.setTelefone(etTelefone.getText().toString());
        aluno.setNota(Double.valueOf(rtNota.getRating()));
        return aluno;


    }

    /**
     * Recupera Aluno no para o Formulario
     * @param alSel
     * Aluno que deseja passar para edição
     */
    public void recuperaAlunoForm(Alunos alSel) {
        etNome.setText(alSel.getNome());
        etEndereco.setText(alSel.getEndereco());
        etSite.setText(alSel.getSite());
        etTelefone.setText(alSel.getTelefone());
        rtNota.setRating(alSel.getNota().floatValue());
    }
}
