package br.dnit.al.wylken.almoxarifadodnital;



import androidx.room.Database;
import androidx.room.RoomDatabase;
import br.dnit.al.wylken.almoxarifadodnital.dao.SalaDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.Sala;

@Database(entities = {Sala.class}, version = 1, exportSchema = false)
public abstract class SalaDatabase extends RoomDatabase {

    public abstract SalaDAO getSalaDAO();
}
