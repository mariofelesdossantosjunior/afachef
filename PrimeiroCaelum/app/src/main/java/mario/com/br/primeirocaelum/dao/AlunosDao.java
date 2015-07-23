package mario.com.br.primeirocaelum.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mario.com.br.primeirocaelum.model.Alunos;

/**
 * Created by mario on 13/11/2014.
 */
public class AlunosDao extends SQLiteOpenHelper{


    private static final String DATABASE = "PrimeiraAula";
    private static final int VERSAO = 4;

    public AlunosDao(Context context) {
        super(context,DATABASE,null,VERSAO);
    }

    public void salva(Alunos alunos) {
        ContentValues valores = new ContentValues();

        valores.put("nome",alunos.getNome());
        valores.put("site",alunos.getSite());
        valores.put("endereco",alunos.getEndereco());
        valores.put("telefone",alunos.getTelefone());
        valores.put("foto",alunos.getFoto());
        valores.put("nota",alunos.getNota());
        getWritableDatabase().insert("Alunos", null, valores);
    }

    public void deletar(Alunos alunos) {
        String[] args = {alunos.getId().toString()};
        getWritableDatabase().delete("Alunos", "id = ?", args);
    }

    /**
     * Cria a Tabelas do base
     * @param db
     * Banco de Dados
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Alunos(id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT UNIQUE NOT NULL, telefone TEXT," +
                "endereco TEXT, site TEXT , foto TEXT, nota REAL);";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(ddl);

    }

    public List<Alunos> getLista() {
        String[] colunas = {"id","nome","site","telefone","endereco","foto","nota"};
        Cursor cursor = getWritableDatabase().query("Alunos", colunas, null, null, null, null, null);
        ArrayList<Alunos> alunosArray = new ArrayList<Alunos>();
        while (cursor.moveToNext()){
            Alunos alunos = new Alunos();
            alunos.setId(cursor.getLong(0));
            alunos.setNome(cursor.getString(1));
            alunos.setSite(cursor.getString(2));
            alunos.setTelefone(cursor.getString(3));
            alunos.setEndereco(cursor.getString(4));
            alunos.setFoto(cursor.getString(5));
            alunos.setNota(cursor.getDouble(6));

            alunosArray.add(alunos);
        }

        return  alunosArray;
    }

    /**
     * Metodo que alterar o aluno
     * @param alu
     * Aluno que deseja alterar
     */
    public void alterarAluno(Alunos alu) {
        Log.i("mario", "Aluno Alterar: "+alu);
        ContentValues valores = new ContentValues();
        valores.put("nome",alu.getNome());
        valores.put("site",alu.getSite());
        valores.put("endereco",alu.getEndereco());
        valores.put("telefone",alu.getTelefone());
        valores.put("foto",alu.getFoto());
        valores.put("nota",alu.getNota());
        String[] args = {alu.getId().toString()};
        getWritableDatabase().update("Alunos", valores, "id=?", args);
    }
}
