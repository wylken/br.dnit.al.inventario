package br.dnit.al.wylken.almoxarifadodnital;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.widget.Toast;
import android.support.annotation.Nullable;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView textView;
    final Activity activity= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        componentesDaTela();

        eventoButton();
    }

    private void eventoButton() {
        btn.setOnClickListener(new View.OnClickListener() {
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
        btn = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.label);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null){
            if (intentResult.getContents() !=  null){
                alert(intentResult.getContents().toString());
            }else{
                alert("Scan cancelado");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
