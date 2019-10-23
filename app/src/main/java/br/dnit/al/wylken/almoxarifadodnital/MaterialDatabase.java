package br.dnit.al.wylken.almoxarifadodnital;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.dnit.al.wylken.almoxarifadodnital.dao.MaterialDAO;
import br.dnit.al.wylken.almoxarifadodnital.models.Material;


@Database(entities = {Material.class}, version = 1)
public abstract class MaterialDatabase extends RoomDatabase{
    public abstract MaterialDAO getMaterialDAO();
}
