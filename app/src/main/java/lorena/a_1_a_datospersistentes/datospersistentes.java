package lorena.a_1_a_datospersistentes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class datospersistentes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datospersistentes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        Button boton1=(Button)findViewById(R.id.botonprimeractividad);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button botoncambiar=(Button)findViewById(R.id.botoncambiar);
        botoncambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(datospersistentes.this, pantallapreferencias.class);
                startActivity(i);
            }
        });
        Button botoncopiar=(Button)findViewById(R.id.botoncopiar);
        botoncopiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String bddestino="/data/data/"+getPackageName()+"/databases/"+basedatos.nome_bdcopiada;
                File file=new File(bddestino);
                if(file.exists()) {
                    Toast.makeText(getApplicationContext(), "A BD non se vai copiar. Xa Existe", Toast.LENGTH_LONG).show();
                    file.delete();
                    return;
                }
                else
                file.mkdirs();
                String pathbd="/data/data/"+getPackageName()+"/databases/";
                File filepathbd=new File(pathbd);
                filepathbd.mkdirs();
                InputStream inputstream;
                try{
                    inputstream=getAssets().open(basedatos.nome_bdcopiada);
                    OutputStream outputstream=new FileOutputStream(bddestino);
                    int tamread;
                    byte[]buffer=new byte[2048];
                    while((tamread=inputstream.read(buffer))>0){
                        outputstream.write(buffer,0,tamread);
                    }
                    inputstream.close();
                    outputstream.flush();
                    outputstream.close();
                    Toast.makeText(getApplicationContext(),"BASE DE DATOS COPIADA",Toast.LENGTH_LONG).show();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_datospersistentes, menu);
        return true;
    }
    public void onClick(View v){
        Intent intent=new Intent(this,altainformacion.class);
        startActivity(intent);
    }
    public void onClick2(View v){
        Intent intent=new Intent(this,Lista.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
