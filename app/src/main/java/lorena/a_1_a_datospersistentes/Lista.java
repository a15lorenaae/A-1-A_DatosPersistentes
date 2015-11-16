package lorena.a_1_a_datospersistentes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Lista extends AppCompatActivity {

    private basedatos datos;
    private Persoas persoa_seleccionada=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        final EditText textonome=(EditText)findViewById(R.id.textonome);
        final EditText textodescricion=(EditText)findViewById(R.id.textodescricion);
        Button botongardar=(Button)findViewById(R.id.botongardar);
        botongardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datpersoa="Persoa:"+persoa_seleccionada.getNome()+" , "+persoa_seleccionada.getDescricion();
                File ruta= Environment.getExternalStorageDirectory();
                SharedPreferences preferencias= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String valornome=preferencias.getString("Ruta", "Datospersoas/");
                File arquivo=new File(ruta,valornome);
                if(!arquivo.exists()) {
                    arquivo.mkdirs();
                }
                File archivo=new File(arquivo,persoa_seleccionada.getNome()+".txt");
                if(archivo.exists()){
                    archivo.delete();
                }

                try{
                    FileOutputStream fos_sdcard=new FileOutputStream(archivo);
                    OutputStreamWriter osw_sdcard=new OutputStreamWriter(fos_sdcard);
                    try {
                        osw_sdcard.write(datpersoa);

                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
        TextView textoseleccionado=(TextView)findViewById(R.id.textoseleccionado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void xestioneventos(){
        ListView lista=(ListView)findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Persoas> adaptador = (ArrayAdapter<Persoas>)
                        parent.getAdapter();
                persoa_seleccionada = adaptador.getItem(position);
                EditText editdescricion = (EditText) findViewById(R.id.textodescricion);
                editdescricion.setText(persoa_seleccionada.getDescricion());
            }
        });

    }
    private void cargarlista(){
        ListView lista=(ListView)findViewById(R.id.lista);
        ArrayList<Persoas> persoas=basedatos.obterpersoas();
        ArrayAdapter<Persoas>adaptador=new ArrayAdapter<Persoas>(getApplicationContext(),android.R.layout.simple_list_item_1,persoas);
        lista.setAdapter(adaptador);
    }

    public void onStart(){
        super.onStart();
        if(datos==null){
            datos=new basedatos(this);
            datos.sqlLiteDB=datos.getWritableDatabase();
        }
    }
    public void onStop(){
        super.onStop();
        if(datos!=null){
            datos.close();
            datos=null;
        }
    }
}
