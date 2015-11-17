package lorena.a_1_a_datospersistentes;

    import android.content.Intent;
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
    import android.widget.Toast;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.OutputStreamWriter;
    import java.util.ArrayList;

    public class Lista extends AppCompatActivity {
        private TextView textoseleccionado;
        private basedatos datos;
        private Persoas persoa_seleccionada = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lista);
            datos = new basedatos(getApplicationContext());
            datos.sqlLiteDB = datos.getWritableDatabase();
            Button botongardar = (Button) findViewById(R.id.botongardar);
            textoseleccionado = (TextView) findViewById(R.id.textoseleccionado);
            final ListView lista = (ListView) findViewById(R.id.lista);

            ArrayList<Persoas> persoa = basedatos.obterpersoas();
            ArrayAdapter<Persoas> adaptador = new ArrayAdapter<Persoas>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, persoa);
            lista.setAdapter(adaptador);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    ArrayAdapter<Persoas> adaptador1 = (ArrayAdapter<Persoas>) arg0.getAdapter();
                    persoa_seleccionada = adaptador1.getItem(arg2);
                    textoseleccionado.setText(persoa_seleccionada.getDescricion());

                }


            });

            botongardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    File ruta = Environment.getExternalStorageDirectory();
                    //Donde estan gardadas as opcións que temos escollidas ata agora.
                    SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    //Aplicar preferencias
                    String valornome = preferencias.getString("valor_directorio", "/DATOS/");
                    File arquivo = new File(ruta, valornome);
                    if (!arquivo.exists()) {
                        arquivo.mkdirs();
                    }
                    File fsaida = new File(arquivo, persoa_seleccionada.getNome() + ".txt");


                    try {
                        FileOutputStream fos_sdcard = new FileOutputStream(fsaida, false);
                        OutputStreamWriter osw_sdcard = new OutputStreamWriter(fos_sdcard);
                        osw_sdcard.write(persoa_seleccionada.getNome() + "," + persoa_seleccionada.getDescricion());
                        osw_sdcard.flush();
                        osw_sdcard.close();
                        fos_sdcard.close();
                        Toast.makeText(getApplicationContext(), "Texto gardado en " + arquivo.getAbsolutePath(), Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            Button botoncambiar=(Button)findViewById(R.id.botoncambiar);
            botoncambiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i;
                    i = new Intent(Lista.this, pantallapreferencias.class);
                    startActivity(i);
                }
            });


        }

    }

