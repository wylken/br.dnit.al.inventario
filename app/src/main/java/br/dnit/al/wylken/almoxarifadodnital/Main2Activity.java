package br.dnit.al.wylken.almoxarifadodnital;

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
import android.widget.Toast;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Main2Activity extends AppCompatActivity {

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
                while ((nextLine = reader.readNext()) != null) {
                    Log.i("MyActivity", "Patrimônio: "+nextLine[7]+", Descrição: "+nextLine[3]+ ", Localização: "+nextLine[6]);
                }
            } catch (IOException e) {
                    Log.e("Error Load File", e.getMessage());
            }

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
