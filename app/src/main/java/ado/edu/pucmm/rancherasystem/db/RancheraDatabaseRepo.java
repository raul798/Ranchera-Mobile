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
    private ProductDao productDao;
    private static final Object LOCK = new Object();

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        public void onCreate (SupportSQLiteDatabase db){

        }
        public void onOpen (SupportSQLiteDatabase db){
            //delete existing data
            db.execSQL("Delete From Client");

            ContentValues contentValuesClients = new ContentValues();
            contentValuesClients.put("name", "Raul Test");
            contentValuesClients.put("phoneNumber", "809-123-4567");
            contentValuesClients.put("email", "raul.test@email.com");
            contentValuesClients.put("address", "Test address #10");
            db.insert("Client", OnConflictStrategy.IGNORE, contentValuesClients);


            db.execSQL("Delete From Product");

            ContentValues contentValuesProduct = new ContentValues();
            contentValuesProduct.put("name", "Product#0001");
            contentValuesProduct.put("quantity", 40);
            contentValuesProduct.put("price", 500);
            contentValuesProduct.put("description", "Producto de testing");
            db.insert("Product", OnConflictStrategy.IGNORE, contentValuesProduct);


        }
    };

    public synchronized static RancheraDB getRancheraDB(Context context){
        if(rancheraDB == null) {
            synchronized (LOCK) {
                if (rancheraDB == null) {
                    rancheraDB = Room.databaseBuilder(context,
                            RancheraDB.class, "ranchera db")
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

    public List<Product> getProduct(Context context, String productStr) {
        if (productDao == null) {
            productDao = RancheraDatabaseRepo.getRancheraDB(context).productDao();
        }
        return productDao.getProducts(productStr+"%");
    }
}
