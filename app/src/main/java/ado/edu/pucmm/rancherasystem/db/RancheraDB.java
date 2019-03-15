package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Client.class, Bill.class, Product.class}, version = 1)
public abstract class RancheraDB extends RoomDatabase {

    public abstract ClientDao clientDao();
    public abstract BillDao billDao();

    private static volatile RancheraDB INSTANCE;

    static RancheraDB getDatabase(final Context context) {

@Database(entities = {Client.class, Bill.class, Product.class, Factura.class, Detalle.class}, version = 1)
public abstract class RancheraDB extends RoomDatabase {
    public abstract ClientDao clientDao();
    public abstract ProductDao productDao();
    public abstract DetalleDao detalleDao();
    public abstract FacturaDao facturaDao();

    private static volatile RancheraDB INSTANCE;

    public static RancheraDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RancheraDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RancheraDB.class, "ranchera_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BillDao mDao;

        PopulateDbAsync(RancheraDB db) {
            mDao = db.billDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }

}