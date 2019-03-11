package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class RancheraDatabaseRepo {
    private static RancheraDB rancheraDB;
    private ClientDao clientDao;
    private ProductDao productDao;
    private FacturaDao facturaDao;
    private DetalleDao detalleDao;
    private static final Object LOCK = new Object();
    private LiveData<List<Product>> allProducts;

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
            contentValuesProduct.put("description", "Producto de testing 1");
            db.insert("Product", OnConflictStrategy.IGNORE, contentValuesProduct);

            contentValuesProduct = new ContentValues();
            contentValuesProduct.put("name", "Product#0002");
            contentValuesProduct.put("quantity", 30);
            contentValuesProduct.put("price", 1000);
            contentValuesProduct.put("description", "Producto de testing 2");
            db.insert("Product", OnConflictStrategy.IGNORE, contentValuesProduct);
        }
    };

    public RancheraDatabaseRepo(Application application) {
        RancheraDB db = RancheraDB.getDatabase(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    public RancheraDatabaseRepo() {}

    public synchronized static RancheraDB getRancheraDB(Context context){
        if(rancheraDB == null) {
            synchronized (LOCK) {
                if (rancheraDB == null) {
                    rancheraDB = Room.databaseBuilder(context,
                            RancheraDB.class, "ranchera_database")
                            .addCallback(dbCallback).build();
                }
            }
        }
        return rancheraDB;
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
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

    public void insert (Product product) {
        new insertAsyncTask(productDao).execute(product);
    }

    public Factura getBill(Context context, int factura_id) {
        if (facturaDao == null) {
            facturaDao = RancheraDatabaseRepo.getRancheraDB(context).facturaDao();
        }
        return facturaDao.searchFacturaByID(factura_id);
    }

    public Client getSingleClient(Context context, int client_id) {
        if (clientDao == null) {
            clientDao = RancheraDatabaseRepo.getRancheraDB(context).clientDao();
        }
        return clientDao.searchClientByID(client_id);
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao asyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
