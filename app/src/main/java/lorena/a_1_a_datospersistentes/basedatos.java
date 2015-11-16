package lorena.a_1_a_datospersistentes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Lorena on 14/11/2015.
 */
public class basedatos extends SQLiteOpenHelper {
    public final static String nome_bd = "persoas";
    public final static int version_bd = 1;
    private final  String consultar_persoas = "SELECT  * from persoas order by nome";
    private final  String taboas_persoas = "persoas";
    public  SQLiteDatabase sqlLiteDB;
private String creartaboapersoas="CREATE TABLE Persoas("+"nome VARCHAR(30) PRIMARY KEY,"+"descricion VARCHAR (70)"+")";
    public basedatos(Context context) {
        super(context, nome_bd, null, version_bd);
    }


    public long engadirpersoa(Persoas persoa) {
        ContentValues valores = new ContentValues();
        valores.put("nome", persoa.getNome());
        valores.put("descricion",persoa.getDescricion());
        long id = sqlLiteDB.insert("Persoas", null, valores);
        return id;

    }



    public ArrayList<Persoas> obterpersoas() {
        ArrayList<Persoas> persoas_devolver = new ArrayList<Persoas>();
        Cursor datosconsulta = sqlLiteDB.rawQuery(consultar_persoas, null);
        if (datosconsulta.moveToFirst()) {
            Persoas persoa;
            while (!datosconsulta.isAfterLast()) {
                persoa = new Persoas(datosconsulta.getString(0), datosconsulta.getString(1));
                persoas_devolver.add(persoa);
                datosconsulta.moveToNext();
            }

        }
        return persoas_devolver;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creartaboapersoas);

    }

    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE ID EXIST Persoas");
        onCreate(db);
    }

}