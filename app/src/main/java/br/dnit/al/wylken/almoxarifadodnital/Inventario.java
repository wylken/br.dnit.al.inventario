package br.dnit.al.wylken.almoxarifadodnital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import br.dnit.al.wylken.almoxarifadodnital.dao.InventarioDAO;
import br.dnit.al.wylken.almoxarifadodnital.dao.MaterialDAO;
import br.dnit.al.wylken.almoxarifadodnital.dao.SalaDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.InventarioModel;
import br.dnit.al.wylken.almoxarifadodnital.models.Material;
import br.dnit.al.wylken.almoxarifadodnital.models.Sala;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Inventario extends AppCompatActivity {

    final Activity activity= this;
    TextView text_sala;
    Button btnBarcode;
    Button btnConsultar;
    EditText editEnterPatrimonio;
    ListView listViewMateriais;
    String sala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);


        Intent intent = getIntent();
        sala = intent.getStringExtra("sala");

        text_sala = (TextView) findViewById(R.id.text_sala);
        text_sala.setText(sala);

        componentesDaTela();
        eventoButton();
        loadList();

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

        this.btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Implementar detalhamento
                String patrimonio = editEnterPatrimonio.getText().toString();
                String sala = text_sala.getText().toString();
                if (!patrimonio.isEmpty() && !sala.isEmpty()) {
                    Intent intent = new Intent(activity, MaterialDetalhe.class);
                    intent.putExtra("patrimonio", patrimonio);
                    intent.putExtra("sala", sala);
                    startActivity(intent);
                }
            }
        });
    }

    private void componentesDaTela() {
        this.btnBarcode = (Button)findViewById(R.id.btn_barcode2);
        this.btnConsultar = (Button) findViewById(R.id.btn_consultar_patrimonio);
        this.editEnterPatrimonio = (EditText) findViewById(R.id.edit_enter_patrimonio);
        this.listViewMateriais = (ListView) findViewById(R.id.list_material_inventario);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 49374 && resultCode == RESULT_OK) {

            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            if(intentResult != null){
                if (intentResult.getContents() !=  null){

                    String patrimonio = intentResult.getContents().toString();

                    editEnterPatrimonio.setText(patrimonio);
                }else{
                    alert("Scan cancelado");
                }
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

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    private void loadList(){
        MaterialDatabase materialDatabase = Room.databaseBuilder(activity, MaterialDatabase.class, "mydb").allowMainThreadQueries().build();
        InventarioDAO inventarioDAO = materialDatabase.getInventarioDAO();

        List<InventarioModel> inventarios = inventarioDAO.getByLocalizacao(sala);
        ArrayAdapter<InventarioModel> inventarioAdapter = new ArrayAdapter<InventarioModel>(activity, android.R.layout.simple_list_item_1, inventarios);
        listViewMateriais.setAdapter(inventarioAdapter);
    }
}
