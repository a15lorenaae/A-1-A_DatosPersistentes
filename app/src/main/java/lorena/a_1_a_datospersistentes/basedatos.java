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
    public final static String nome_bd = "datos";
    public final static int version_bd = 1;
    public final static String consultar_datos = "Select nome,descricion from datos order by nome";
    private final  String taboas_persoas = "Datos";
    public static SQLiteDatabase sqlLiteDB;
private static String creartaboadatos="CREATE TABLE datos("+"nome VARCHAR(30) PRIMARY KEY,"+"descricion VARCHAR (70)"+")";
    public  basedatos(Context context) {
        super(context, nome_bd, null, version_bd);
    }


    public static long engadirpersoa(Persoas persoa) {
        ContentValues valores = new ContentValues();
        valores.put("nome", persoa.getNome());
        valores.put("descricion",persoa.getDescricion());
        long id = sqlLiteDB.insert("Datos", null, valores);
        return id;

    }



    public static ArrayList<Persoas> obterpersoas() {
        ArrayList<Persoas> persoas_devolver = new ArrayList<Persoas>();
        Cursor datosconsulta = sqlLiteDB.rawQuery(consultar_datos, null);
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

        db.execSQL(creartaboadatos);

    }

    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS datos");
        onCreate(db);
    }

}