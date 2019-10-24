package br.dnit.al.wylken.almoxarifadodnital.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sala")
public class Sala {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String nome;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
