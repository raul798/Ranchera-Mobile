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
import java.util.concurrent.ExecutionException;

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

    public Client getSingleClient (Context context, Integer clientId) {
        Client client = null;
        if(clientDao == null) {
            clientDao = RancheraDatabaseRepo.getRancheraDB(context).clientDao();

            try {
                client = new clientAsyncTask(clientDao).execute(clientId).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public Factura getBill(Context context, int factura_id) {
        if (facturaDao == null) {
            facturaDao = RancheraDatabaseRepo.getRancheraDB(context).facturaDao();
        }

        Factura bill = null;

        try {
            bill = new billAsyncTask(facturaDao).execute(factura_id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return bill;
    }

    public List<Integer> getProductsFromDetail(Context context, Integer factura_id) {
        if (detalleDao == null) {
            detalleDao = RancheraDatabaseRepo.getRancheraDB(context).detalleDao();
        }

        List <Integer> products = null;

        try {
            products = new detalleAsyncTask(detalleDao).execute(factura_id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getOrderProduct(Context context, Integer factura_id) {
        if (productDao == null) {
            productDao = RancheraDatabaseRepo.getRancheraDB(context).productDao();
        }

        Product product = null;

        try {
            product = new productAsyncTask(productDao).execute(factura_id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return product;
    }

    public Integer getSelectedProductAmount(Context context, Integer product_id) {
        if (detalleDao == null) {
            detalleDao = RancheraDatabaseRepo.getRancheraDB(context).detalleDao();
        }

        Integer amount = null;

        try {
            amount = new amountAsyncTask(detalleDao).execute(product_id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return amount;
    }

    private static class detalleAsyncTask extends AsyncTask<Integer, Void, List <Integer>>{

        private DetalleDao asyncTaskDao;

        detalleAsyncTask(DetalleDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected List<Integer> doInBackground(final Integer... params){
            return asyncTaskDao.getBillProducts(params[0]);
        }
    }

    private static class amountAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private DetalleDao asyncTaskDao;

        amountAsyncTask(DetalleDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Integer... params){
            return asyncTaskDao.getSelectedProductAmount(params[0]);
        }
    }
    private static class productAsyncTask extends AsyncTask<Integer, Void, Product>{

        private ProductDao asyncTaskDao;

        productAsyncTask(ProductDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Product doInBackground(final Integer... params){
            return asyncTaskDao.searchProductByID(params[0]);
        }
    }

    private static class clientAsyncTask extends AsyncTask<Integer, Void, Client>{

        private ClientDao asyncTaskDao;

        clientAsyncTask(ClientDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Client doInBackground(final Integer... params){
             return asyncTaskDao.searchClientByID(params[0]);
        }
    }

    public static class billAsyncTask extends AsyncTask<Integer, Void, Factura>{

        private FacturaDao asyncTaskDao;

        billAsyncTask(FacturaDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Factura doInBackground(Integer... integers) {
            return asyncTaskDao.searchFacturaByID(integers[0]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Product, Integer, Void> {

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
