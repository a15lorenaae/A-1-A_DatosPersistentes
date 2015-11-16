package lorena.a_1_a_datospersistentes;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    TextView textoseleccionado;
    private basedatos datos;
    private Persoas persoa_seleccionada=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Button botongardar=(Button)findViewById(R.id.botongardar);
        textoseleccionado=(TextView)findViewById(R.id.textoseleccionado);
        final ListView lista=(ListView)findViewById(R.id.lista);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, marcas);
        <ArrayAdapter>
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ArrayAdapter<Persoas>adaptador=(ArrayAdapter<Persoas>)arg0.getAdapter();
                persoa_seleccionada=adaptador.getItem(arg2);
                EditText textonome=(EditText)findViewById(R.id.textonome);
                EditText textodescricion=(EditText)findViewById(R.id.textodescricion);
                textonome.setText(persoa_seleccionada.getNome());
                textodescricion.setText(persoa_seleccionada.getDescricion());
                textoseleccionado.setText(basedatos.consultar_persoas);
                ArrayList<Persoas> persoas=basedatos.obterpersoas();
                lista.setAdapter(adaptador);

            }
        });

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


    }


    public void onStart(){
        super.onStart();
        if(datos==null){
            datos=new basedatos(this);
            datos.sqlLiteDB=datos.getReadableDatabase();
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


