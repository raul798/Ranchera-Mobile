package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Client.class, Bill.class, Product.class}, version = 1)
public abstract class RancheraDB extends RoomDatabase {

    public abstract ClientDao clientDao();
    public abstract ProductDao productDao();

    private static volatile RancheraDB INSTANCE;

    static RancheraDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RancheraDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RancheraDB.class, "ranchera_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}