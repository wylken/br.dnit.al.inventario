package br.dnit.al.wylken.almoxarifadodnital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import br.dnit.al.wylken.almoxarifadodnital.dao.SalaDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.Sala;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

public class NewInventario extends AppCompatActivity {

    Button btn_next;
    Spinner spinner;
    EditText edit_new_sala;

    final Activity activity= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_inventario);
        componentesDaTela();
        eventoButton();

        MaterialDatabase materialDatabase = Room.databaseBuilder(this, MaterialDatabase.class, "mydb").allowMainThreadQueries().build();
        SalaDAO salaDAO = materialDatabase.getSalaDAO();
        List<String> salas = salaDAO.getAll();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, salas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



    }

    private void componentesDaTela() {
        this.btn_next = (Button)findViewById(R.id.btn_next);
        this.spinner = (Spinner) findViewById(R.id.selecionar_sala);
        this.edit_new_sala = (EditText) findViewById(R.id.new_sala);
    }

    private void eventoButton() {

        //BTN NEXT
        this.btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sala = edit_new_sala.getText().toString();
                if(sala.isEmpty()){
                    sala = spinner.getSelectedItem().toString();
                }

                Intent intent = new Intent(activity, Inventario.class);
                intent.putExtra("sala",sala);
                startActivity(intent);
            }
        });

    }

}
