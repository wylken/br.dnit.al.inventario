package br.dnit.al.wylken.almoxarifadodnital.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.dnit.al.wylken.almoxarifadodnital.models.InventarioModel;

@Dao
public interface InventarioDAO {

    @Insert
    public void insert(InventarioModel... inventarios);

    @Update
    public void update(InventarioModel... inventarios);
    @Delete
    public void delete(InventarioModel inventario);

    @Query("SELECT * FROM inventario")
    public List<InventarioModel> getAll();

    @Query("SELECT * FROM inventario where salaInventario like :localizacao")
    public List<InventarioModel> getByLocalizacao(String localizacao);

    @Query("SELECT DISTINCT salaInventario FROM inventario")
    public List<String> getDistinctSala();

}
