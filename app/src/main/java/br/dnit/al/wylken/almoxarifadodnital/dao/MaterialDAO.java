package br.dnit.al.wylken.almoxarifadodnital.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
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

    @Query("SELECT DISTINCT localizacao FROM material")
    public List<String> getSalas();
}
