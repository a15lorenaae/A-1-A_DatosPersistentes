package lorena.a_1_a_datospersistentes;

import android.app.Activity;
import android.os.Bundle;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class altainformacion extends Activity {
    private basedatos datos;
    private Persoas persoa_seleccionada=null;
    EditText editnome;
    EditText editdescricion;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altainformacion);
        editnome = (EditText) findViewById(R.id.textonome);
        editdescricion = (EditText) findViewById(R.id.textodescricion);
        Button botonalta=(Button)findViewById(R.id.botonalta);
        botonalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Persoas persoa = new Persoas(editnome.getText().toString(), editdescricion.getText().toString());
                basedatos.engadirpersoa(persoa);
                editnome.setText("");
                editdescricion.setText("");
                 }
        });


    }


}
