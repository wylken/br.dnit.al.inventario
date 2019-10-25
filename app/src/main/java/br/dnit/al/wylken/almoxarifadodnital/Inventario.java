package br.dnit.al.wylken.almoxarifadodnital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Inventario extends AppCompatActivity {

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);


        Intent intent = getIntent();
        String sala = intent.getStringExtra("sala");

        test = (TextView) findViewById(R.id.text_sala);
        test.setText(sala);

    }
}
