package br.dnit.al.wylken.almoxarifadodnital;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import br.dnit.al.wylken.almoxarifadodnital.dao.InventarioDAO;
import br.dnit.al.wylken.almoxarifadodnital.dao.MaterialDAO;
import br.dnit.al.wylken.almoxarifadodnital.dao.SalaDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.InventarioModel;
import br.dnit.al.wylken.almoxarifadodnital.models.Material;
import br.dnit.al.wylken.almoxarifadodnital.models.Sala;


@Database(entities = {Material.class, Sala.class, InventarioModel.class}, version = 1, exportSchema = false)
public abstract class MaterialDatabase extends RoomDatabase {
    public abstract MaterialDAO getMaterialDAO();
    public abstract SalaDAO getSalaDAO();
    public abstract InventarioDAO getInventarioDAO();
}
