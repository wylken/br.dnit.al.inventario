package br.dnit.al.wylken.almoxarifadodnital.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventario")
public class InventarioModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private  String salaInventario;
    private String patrimonio;
    private String descricao;
    private String detentor;
    private String localizacao;
    private String observacao;

    @Override
    public String toString(){
        return this.patrimonio+" - "+this.descricao;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDetentor() {
        return detentor;
    }

    public void setDetentor(String detentor) {
        this.detentor = detentor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getSalaInventario() {
        return salaInventario;
    }

    public void setSalaInventario(String salaInventario) {
        this.salaInventario = salaInventario;
    }
}
