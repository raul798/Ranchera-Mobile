package ado.edu.pucmm.rancherasystem;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Client.class}, version = 1)
public abstract class RancheraRoomDatabase extends RoomDatabase {
    private static volatile RancheraRoomDatabase INSTANCE;

    public abstract ClientDao clientDao();

    static RancheraRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RancheraRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RancheraRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

