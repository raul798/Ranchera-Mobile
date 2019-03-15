package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class RancheraDatabaseRepo {

    private static RancheraDB rancheraDB;
    private ClientDao clientDao;
    private BillDao billDao;
    private List<Bill> listofbills; //TODO: initialize this variable
    private static final Object LOCK = new Object();

    public RancheraDatabaseRepo(Context context){
        RancheraDB db = RancheraDB.getDatabase(context);
        billDao = db.billDao();
        listofbills = new ArrayList<>();
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        public void onCreate (SupportSQLiteDatabase db){}
        public void onOpen (SupportSQLiteDatabase db){}
    };


import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
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
                            RancheraDB.class, "ranchera db")
                            RancheraDB.class, "ranchera_database")
                            .addCallback(dbCallback).build();
                }
            }
        }
        return rancheraDB;
    }

    public void insert(Bill bill){
        new insertAsyncTask().execute(bill);
    }

    private class insertAsyncTask extends AsyncTask<Bill, Void, Void> {

        insertAsyncTask() {
        }

        @Override
        protected Void doInBackground(final Bill... params) {
            billDao.insert(params[0]);
            return null;
        }
    }

    public List<Bill> getBills(Context context, Integer client_id) {
        if (billDao == null) {
            billDao = RancheraDatabaseRepo.getRancheraDB(context).billDao();
        }
        List<Bill> bills = new ArrayList<>();
        try {
            bills = new selectBillsAsyncTask(billDao).execute(client_id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }

    private static class selectBillsAsyncTask extends AsyncTask<Integer, Void, List<Bill>>{

        private BillDao mAsyncTaskDao;

        selectBillsAsyncTask(BillDao billdao){
            mAsyncTaskDao = billdao;
        }

        @Override
        protected List<Bill> doInBackground(Integer... params) {
            return mAsyncTaskDao.getBills(params[0]);
        }
    }

    public List<Client> getClient(Context context, String clientStr) {
        if (clientDao == null) {
            clientDao = RancheraDatabaseRepo.getRancheraDB(context).clientDao();
        }
        return clientDao.getClients(clientStr+"%");
    }

    public List<Bill> getListofbills() {
        return listofbills;

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

    public void updateBillTotal(Context context, Integer bill_id, Float total) {
        if (facturaDao == null) {
            facturaDao = RancheraDatabaseRepo.getRancheraDB(context).facturaDao();
        }

        updateParams params = new updateParams(bill_id,total);

        new updateTotalAsyncTask(facturaDao).execute(params);
    }

    public void updateBillDescription(Context context, Integer bill_id, String description){

        if (facturaDao == null) {
            facturaDao = RancheraDatabaseRepo.getRancheraDB(context).facturaDao();
        }

        updateParamsDescription params = new updateParamsDescription(bill_id,description);

        new updateDescriptionAsyncTask(facturaDao).execute(params);
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

    private static class insertAsyncTask extends android.os.AsyncTask<Product, Integer, Void> {

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

    private static class updateParams {
        int id;
        float total;

        updateParams(int id, float total) {
            this.id = id;
            this.total = total;
        }
    }

    private static class updateTotalAsyncTask extends AsyncTask<updateParams, Void, Void> {

        private FacturaDao asyncTaskDao;

        updateTotalAsyncTask(FacturaDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateParams... params) {
            asyncTaskDao.updateBillTotal(params[0].id, params[0].total);
            return null;
        }
    }

    private static class updateParamsDescription {
        int id;
        String description;

        updateParamsDescription(int id, String description) {
            this.id = id;
            this.description = description;
        }
    }

    private static class updateDescriptionAsyncTask extends AsyncTask<updateParamsDescription, Void, Void> {

        private FacturaDao asyncTaskDao;

        updateDescriptionAsyncTask(FacturaDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateParamsDescription... params) {
            asyncTaskDao.updateBillDescription(params[0].id, params[0].description);
            return null;
        }
    }

    public void updateBillSignature(Context context, Integer bill_id, byte[] signature){

        if (facturaDao == null) {
            facturaDao = RancheraDatabaseRepo.getRancheraDB(context).facturaDao();
        }

        updateParamsSignature params = new updateParamsSignature(bill_id,signature);

        new updateSignatureAsyncTask(facturaDao).execute(params);
    }

    private static class updateParamsSignature {
        int id;
        byte[] signature;

        updateParamsSignature(int id, byte[] signature) {
            this.id = id;
            this.signature = signature;
        }
    }

    private static class updateSignatureAsyncTask extends AsyncTask<updateParamsSignature, Void, Void> {

        private FacturaDao asyncTaskDao;

        updateSignatureAsyncTask(FacturaDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateParamsSignature... params) {
            asyncTaskDao.updateBillSignature(params[0].id, params[0].signature);
            return null;
        }
    }
}
