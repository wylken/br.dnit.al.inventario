package br.dnit.al.wylken.almoxarifadodnital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import br.dnit.al.wylken.almoxarifadodnital.dao.InventarioDAO;
import br.dnit.al.wylken.almoxarifadodnital.dao.MaterialDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.InventarioModel;
import br.dnit.al.wylken.almoxarifadodnital.models.Material;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MaterialDetalhe extends AppCompatActivity {

    final Activity activity= this;

    TextView textPatrimonio;
    TextView textDescricao;
    TextView textLocalizacao;
    EditText editTextDetentor;
    Button btnSalvar;
    Material material;
    String sala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detalhe);

        componentesDaTela();
        eventoButton();

        Intent intent = getIntent();
        String patrimonio = intent.getStringExtra("patrimonio");
        sala = intent.getStringExtra("sala");

        MaterialDatabase materialDatabase = Room.databaseBuilder(this, MaterialDatabase.class, "mydb").allowMainThreadQueries().build();
        MaterialDAO materialDAO = materialDatabase.getMaterialDAO();
        //if(patrimonio.length() < 9){
            patrimonio = "%"+patrimonio+"%";
        //}
        List<Material> materiais = materialDAO.getByPatrimonio(patrimonio);
        material = materiais.get(0);

        textPatrimonio.setText(material.getPatrimonio());
        textDescricao.setText(material.getDescricao());
        textLocalizacao.setText(material.getLocalizacao());
        editTextDetentor.setText(material.getDetentor());




    }

    private void componentesDaTela() {
        this.textPatrimonio = (TextView) findViewById(R.id.detalhe_patrimonio);
        this.textDescricao = (TextView) findViewById(R.id.detalhe_descricao);
        this.textLocalizacao = (TextView) findViewById(R.id.detalhe_localizacao);
        this.editTextDetentor = (EditText) findViewById(R.id.detalhe_detentor);
        this.btnSalvar = (Button) findViewById(R.id.btn_save_inventario);
    }

    private void eventoButton() {

        //BTN NEXT
        this.btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDatabase materialDatabase = Room.databaseBuilder(activity, MaterialDatabase.class, "mydb").allowMainThreadQueries().build();
                InventarioDAO inventarioDAO = materialDatabase.getInventarioDAO();

                InventarioModel inventario = new InventarioModel();
                inventario.setPatrimonio(material.getPatrimonio());
                inventario.setDescricao(material.getDescricao());
                inventario.setDetentor(material.getDetentor());
                inventario.setLocalizacao(material.getLocalizacao());
                inventario.setSalaInventario(sala);

                String observacao = new String("");
                String detentorAtual = editTextDetentor.getText().toString();
                String localizacaoAtual = textLocalizacao.getText().toString();
                if (!material.getDetentor().equals(detentorAtual)){
                    observacao = observacao + "* Mudar DETENTOR para -> "+ detentorAtual+" | ";
                }
                if (!material.getLocalizacao().equals(sala)){
                    observacao = observacao + "* Mudar LOCALIZAÇÃO para -> "+ sala+" | ";
                }

                inventario.setObservacao(observacao);

                inventarioDAO.insert(inventario);

                activity.finish();

            }
        });

    }

}
