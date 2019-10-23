package br.dnit.al.wylken.almoxarifadodnital.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.dnit.al.wylken.almoxarifadodnital.models.Material;

@Dao
public interface MaterialDAO {

    @Insert
    public void insert(Material... materiais);

    @Update
    public void update(Material... materiais);
    @Delete
    public void delete(Material material);

    @Query("SELECT * FROM material")
    public List<Material> getMateriais();

    @Query("DELETE FROM material")
    public void deleteAll();

    @Query("SELECT * FROM material WHERE patrimonio LIKE :patrimonio")
    public List<Material> getByPatrimonio(String patrimonio) ;
}
