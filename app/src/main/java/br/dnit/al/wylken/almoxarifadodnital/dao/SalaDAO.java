package br.dnit.al.wylken.almoxarifadodnital.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.dnit.al.wylken.almoxarifadodnital.models.Sala;

@Dao
public interface SalaDAO {

    @Insert
    public void insert(Sala... salas);
    @Update
    public void update(Sala... salas);
    @Delete
    public void delete(Sala sala);

    @Query("DELETE FROM sala")
    public void deleteAll();
}
