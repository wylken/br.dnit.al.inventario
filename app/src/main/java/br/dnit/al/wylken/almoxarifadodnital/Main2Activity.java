package br.dnit.al.wylken.almoxarifadodnital;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import br.dnit.al.wylken.almoxarifadodnital.dao.MaterialDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.Material;


public class Main2Activity extends AppCompatActivity {

    Button btnBarcode;
    TextView textResult;
    final Activity activity= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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

        componentesDaTela();
        eventoButton();

        Stetho.initializeWithDefaults(this);
    }

    private void eventoButton() {
        this.btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
            }
        });
    }

    private void componentesDaTela() {
        this.btnBarcode = (Button)findViewById(R.id.btn_barcode);
        this.textResult = (TextView)findViewById(R.id.text_result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
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

            } catch (IOException e) {
                Log.e("Error Load File", e.getMessage());
            }

        }

        if(requestCode == 49374 && resultCode == RESULT_OK) {

            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

            if(intentResult != null){
                if (intentResult.getContents() !=  null){

                    MaterialDatabase materialDatabase = Room.databaseBuilder(this, MaterialDatabase.class, "mydb").allowMainThreadQueries().build();
                    MaterialDAO materialDAO = materialDatabase.getMaterialDAO();
                    //List<Material> materiais = materialDAO.getMateriais();
                    String patrimonio = intentResult.getContents().toString();
                    if(patrimonio.length() < 9){
                        patrimonio = "0"+patrimonio;
                    }
                    List <Material> materiais = materialDAO.getByPatrimonio(patrimonio);
                    textResult.setText(materiais.get(0).getDescricao());
                    //textResult.setText(intentResult.getContents().toString());
                }else{
                    alert("Scan cancelado");
                }
            }
        }

        else{
            alert(new Integer(requestCode).toString());
        }
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
}
