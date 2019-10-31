package br.dnit.al.wylken.almoxarifadodnital;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.opencsv.CSVReader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import br.dnit.al.wylken.almoxarifadodnital.dao.InventarioDAO;
import br.dnit.al.wylken.almoxarifadodnital.dao.MaterialDAO;
import br.dnit.al.wylken.almoxarifadodnital.dao.SalaDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.InventarioModel;
import br.dnit.al.wylken.almoxarifadodnital.models.Material;
import br.dnit.al.wylken.almoxarifadodnital.models.Sala;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listSalasIventario;
    final Activity activity= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(activity, NewInventario.class);
               startActivity(intent);
            }
        });

        componentesDaTela();
        eventoButton();

        loadList();

        Stetho.initializeWithDefaults(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.load_data:
                loadData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eventoButton() {

    }

    private void componentesDaTela() {
        this.listSalasIventario = (ListView) findViewById(R.id.list_salas_inventario);
    }

    private void loadData(){

        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_OPEN_DOCUMENT);

        startActivityForResult(intent, 123);
    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData();
            alert(selectedfile.toString());

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedfile);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                CSVReader reader = new CSVReader(inputStreamReader, ',');
                String[] nextLine;

                MaterialDatabase materialDatabase = Room.databaseBuilder(this, MaterialDatabase.class, "mydb").allowMainThreadQueries().build();
                MaterialDAO materialDAO = materialDatabase.getMaterialDAO();
                materialDAO.deleteAll();
                int flag = 0;
                while ((nextLine = reader.readNext()) != null) {
                    if (flag > 0) {
                        Material material = new Material();
                        material.setPatrimonio(nextLine[7]);
                        material.setDescricao(nextLine[3]);
                        material.setDetentor(nextLine[4]);
                        material.setLocalizacao(nextLine[6]);
                        materialDAO.insert(material);
                    }

                    flag+=1; //Discart first element



                }

                //SalaDatabase salaDatabase = Room.databaseBuilder(this, SalaDatabase.class, "mydb").allowMainThreadQueries().build();
                SalaDAO salaDAO = materialDatabase.getSalaDAO();
                salaDAO.deleteAll();



                List<String> salasString = materialDAO.getSalas();

                for(String s : salasString){

                    Sala sala = new Sala();
                    sala.setNome(s);
                    salaDAO.insert(sala);
                }




            } catch (IOException e) {
                Log.e("Error Load File", e.getMessage());
            }

        }

        else{
            alert(new Integer(requestCode).toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    private void loadList() {
        MaterialDatabase materialDatabase = Room.databaseBuilder(activity, MaterialDatabase.class, "mydb").allowMainThreadQueries().build();
        InventarioDAO inventarioDAO = materialDatabase.getInventarioDAO();
        List<String> salas = inventarioDAO.getDistinctSala();

        ArrayAdapter<String> salasAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, salas);
        this.listSalasIventario.setAdapter(salasAdapter);
    }

}
