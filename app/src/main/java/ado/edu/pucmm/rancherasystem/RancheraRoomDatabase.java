package ado.edu.pucmm.rancherasystem;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Client.class}, version = 1)
public abstract class RancheraRoomDatabase extends RoomDatabase {
    public abstract ClientDao clientDao();
}

