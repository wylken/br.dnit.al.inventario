package br.dnit.al.wylken.almoxarifadodnital.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "material")
public class Material {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String patrimonio;
    private String descricao;
    private String detentor;
    private String localizacao;

    @NonNull
    public Long getId() {
        return id;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDetentor() {
        return detentor;
    }

    public String getLocalizacao() {
        return localizacao;
    }


    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDetentor(String detentor) {
        this.detentor = detentor;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }
}
