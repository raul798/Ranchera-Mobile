package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;

import java.util.List;

public class RancheraDatabaseRepo {
    private static RancheraDB rancheraDB;
    private ClientDao clientDao;
    private FacturaDao facturaDao;
    private static final Object LOCK = new Object();

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        public void onCreate (SupportSQLiteDatabase db){

        }
        public void onOpen (SupportSQLiteDatabase db){
            //delete existing data
            db.execSQL("Delete From Client");

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "Raul Test");
            contentValues.put("phoneNumber", "809-123-4567");
            contentValues.put("email", "raul.test@email.com");
            contentValues.put("address", "Test address #10");
            db.insert("Client", OnConflictStrategy.IGNORE, contentValues);

            //Factura
            db.execSQL("Delete From Factura");

            ContentValues factura = new ContentValues();
            factura.put("descripcion", "factura de prueba");
            db.insert("Factura", OnConflictStrategy.IGNORE, factura);

        }
    };

    public synchronized static RancheraDB getRancheraDB(Context context){
        if(rancheraDB == null) {
            synchronized (LOCK) {
                if (rancheraDB == null) {
                    rancheraDB = Room.databaseBuilder(context,
                            RancheraDB.class, "ranchera.db")
                            .addCallback(dbCallback).build();
                }
            }
        }
        return rancheraDB;
    }


    public List<Client> getClient(Context context, String clientStr) {
        if (clientDao == null) {
            clientDao = RancheraDatabaseRepo.getRancheraDB(context).clientDao();
        }
        return clientDao.getClients(clientStr+"%");
    }
}
