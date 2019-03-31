package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.dao.BillDao;
import ado.edu.pucmm.rancherasystem.dao.ClientDao;
import ado.edu.pucmm.rancherasystem.dao.DetailDao;
import ado.edu.pucmm.rancherasystem.dao.ProductDao;
import ado.edu.pucmm.rancherasystem.dao.RouteDao;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.entity.Route;

public class RanchDatabaseRepo {

    private static RanchDb ranchDb;
    private ClientDao clientDao;
    private ProductDao productDao;
    private DetailDao detailDao;
    private BillDao billDao;
    private RouteDao routeDao;
    private static final Object LOCK = new Object();
    private LiveData<List<Product>> allProducts;

    public RanchDatabaseRepo(Context context) {
        RanchDb db = RanchDatabaseRepo.getDb(context);
        productDao = db.getProductDao();
        allProducts = productDao.getAllProducts();
    }

    public RanchDatabaseRepo() {}


    public synchronized static RanchDb getDb(Context context) {
        if (ranchDb == null) {
            synchronized (LOCK) {
                if (ranchDb == null)
                    ranchDb = Room.databaseBuilder(context, RanchDb.class, "ranchera.db")
                            .addCallback(dbCallback)
                            .build();
            }
        }
        return ranchDb;
    }

    public void insert(Bill bill) {
        new InsertAsyncTask().execute(bill);
    }

    private class InsertAsyncTask extends AsyncTask<Bill, Void, Void> {
        @Override
        protected Void doInBackground(final Bill... params) {
            billDao.insert(params[0]);
            return null;
        }
    }

    public List<Bill> getBills(Context context, Integer client_id) {
        if (billDao == null) {
            billDao = RanchDatabaseRepo.getDb(context).getBillDao();
        }
        List<Bill> bills = new ArrayList<>();
        try {
            bills = new SelectBillsAsyncTask(billDao).execute(client_id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }

    private static class SelectBillsAsyncTask extends AsyncTask<Integer, Void, List<Bill>> {

        private BillDao mAsyncTaskDao;

        SelectBillsAsyncTask(BillDao billdao) {
            mAsyncTaskDao = billdao;
        }

        @Override
        protected List<Bill> doInBackground(Integer... params) {
            return mAsyncTaskDao.getBills(params[0]);
        }
    }

    public List<Client> getClient(Context context, String clientStr) {
        if (clientDao == null) {
            clientDao = RanchDatabaseRepo.getDb(context).getClientDao();
        }
        return clientDao.getClients(clientStr + "%");
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public List<Product> getProduct(Context context, String productStr) {
        if (productDao == null) {
            productDao = RanchDatabaseRepo.getDb(context).getProductDao();
        }
        return productDao.getProducts(productStr + "%");
    }

    public void insert(Product product) {
        new InsertAsyncTask().execute();
    }

    public Client getSingleClient(Context context, Integer clientId) {
        Client client = null;
        clientDao = clientDao == null? RanchDatabaseRepo.getDb(context).getClientDao(): clientDao;
        try {
            client = new clientAsyncTask(clientDao).execute(clientId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public Bill getBill(Context context, int billId) {
        if (billDao == null) {
            billDao = RanchDatabaseRepo.getDb(context).getBillDao();
        }

        Bill bill = null;

        try {
            bill = new billAsyncTask(billDao).execute(billId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bill;
    }

    public List<Integer> getProductsFromDetail(Context context, Integer factura_id) {
        if (detailDao == null) {
            detailDao = RanchDatabaseRepo.getDb(context).getDetailDao();
        }

        List<Integer> products = null;

        try {
            products = new detalleAsyncTask(detailDao).execute(factura_id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getOrderProduct(Context context, Integer factura_id) {
        if (productDao == null) {
            productDao = RanchDatabaseRepo.getDb(context).getProductDao();
        }

        Product product = null;

        try {
            product = new productAsyncTask(productDao).execute(factura_id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    public Integer getSelectedProductAmount(Context context, Integer product_id) {
        if (detailDao == null) {
            detailDao = RanchDatabaseRepo.getDb(context).getDetailDao();
        }

        Integer amount = null;

        try {
            amount = new amountAsyncTask(detailDao).execute(product_id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return amount;
    }

    public void updateBillTotal(Context context, Integer bill_id, Float total) {
        if (billDao == null) {
            billDao = RanchDatabaseRepo.getDb(context).getBillDao();
        }

        updateParams params = new updateParams(bill_id, total);

        new updateTotalAsyncTask(billDao).execute(params);
    }

    public void updateBillDescription(Context context, Integer bill_id, String description) {

        if (billDao == null) {
            billDao = RanchDatabaseRepo.getDb(context).getBillDao();
        }

        updateParamsDescription params = new updateParamsDescription(bill_id, description);

        new updateDescriptionAsyncTask(billDao).execute(params);
    }

    private static class detalleAsyncTask extends AsyncTask<Integer, Void, List<Integer>> {

        private DetailDao asyncTaskDao;

        detalleAsyncTask(DetailDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Integer> doInBackground(final Integer... params) {
            return asyncTaskDao.getBillProducts(params[0]);
        }
    }

    private static class amountAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private DetailDao asyncTaskDao;

        amountAsyncTask(DetailDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Integer... params) {
            return asyncTaskDao.getSelectedProductAmount(params[0]);
        }
    }

    private static class productAsyncTask extends AsyncTask<Integer, Void, Product> {

        private ProductDao asyncTaskDao;

        productAsyncTask(ProductDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Product doInBackground(final Integer... params) {
            return asyncTaskDao.searchProductByID(params[0]);
        }
    }

    private static class clientAsyncTask extends AsyncTask<Integer, Void, Client> {

        private ClientDao asyncTaskDao;

        clientAsyncTask(ClientDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Client doInBackground(final Integer... params) {
            return asyncTaskDao.searchClientByID(params[0]);
        }
    }

    public static class billAsyncTask extends AsyncTask<Integer, Void, Bill> {

        private BillDao asyncTaskDao;

        billAsyncTask(BillDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Bill doInBackground(Integer... integers) {
            return asyncTaskDao.searchFacturaByID(integers[0]);
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

        private BillDao asyncTaskDao;

        updateTotalAsyncTask(BillDao dao) {
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

        private BillDao asyncTaskDao;

        updateDescriptionAsyncTask(BillDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateParamsDescription... params) {
            asyncTaskDao.updateBillDescription(params[0].id, params[0].description);
            return null;
        }
    }

    public void updateBillSignature(Context context, Integer bill_id, byte[] signature) {

        if (billDao == null) {
            billDao = RanchDatabaseRepo.getDb(context).getBillDao();
        }

        updateParamsSignature params = new updateParamsSignature(bill_id, signature);

        new updateSignatureAsyncTask(billDao).execute(params);
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

        private BillDao asyncTaskDao;

        updateSignatureAsyncTask(BillDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateParamsSignature... params) {
            asyncTaskDao.updateBillSignature(params[0].id, params[0].signature);
            return null;
        }
    }

    public List<Route> getAllRoutes() {

        if (routeDao == null) {
            routeDao = ranchDb.getRouteDao();
        }

        List<Route> routes = null;

        try {
            routes = new AllRoutesAsyncTask(routeDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return routes;
    }


    private class AllRoutesAsyncTask extends AsyncTask<Void, Void, List<Route>>{

        RouteDao routeDao;

        public AllRoutesAsyncTask(RouteDao routeDao){this.routeDao = routeDao;}

        @Override
        protected List<Route> doInBackground(Void... voids) {
            return routeDao.getAllRoutes();
        }
    }

    public void updateRouteStatus(Context context, boolean status, int clientId) {

        if (routeDao == null) {
            routeDao = RanchDatabaseRepo.getDb(context).getRouteDao();
        }

        updateRouteParams params = new updateRouteParams(status, clientId);

        new updateStatusAsyncTask(routeDao).execute(params);
    }

    private static class updateStatusAsyncTask extends AsyncTask<updateRouteParams, Void, Void> {

        private RouteDao asyncTaskDao;

        updateStatusAsyncTask(RouteDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateRouteParams... params) {
            asyncTaskDao.updateStatus(params[0].status, params[0].clientId);
            return null;
        }
    }

    private static class updateRouteParams {
        boolean status;
        int clientId;

        updateRouteParams(boolean status, int clientId) {
            this.status = status;
            this.clientId = clientId;
        }
    }


    private static class searchRouteByClientIdAsyncTask extends AsyncTask<Integer, Void, Route> {

        private RouteDao asyncTaskDao;

        searchRouteByClientIdAsyncTask(RouteDao dao) {
            asyncTaskDao = dao;
        }


        @Override
        protected Route doInBackground(final Integer... params) {
            return asyncTaskDao.searchRouteByClientId(params[0]);
        }
    }


    public Route getRouteByClientId(Context context, Integer clientId) {
        if (routeDao == null) {
            routeDao = RanchDatabaseRepo.getDb(context).getRouteDao();
        }

        Route route = null;

        try {
            route = new searchRouteByClientIdAsyncTask(routeDao).execute(clientId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return route;
    }



    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {
        }

        public void onOpen(SupportSQLiteDatabase db) {

            /*
            //delete existing data
            db.execSQL("Delete From Client");

            ContentValues client1 = new ContentValues();
            client1.put("name", "Raul Test");
            client1.put("phoneNumber", "809-123-4567");
            client1.put("email", "raul.test@email.com");
            client1.put("address", "Calle Sanabacoa, Santo Domingo");
            db.insert("Client", OnConflictStrategy.IGNORE, client1);

            ContentValues client2 = new ContentValues();
            client2.put("name", "Dante Fana");
            client2.put("phoneNumber", "829-123-4567");
            client2.put("email", "dante.test@email.com");
            client2.put("address", "Calle Sanabacoa, Santo Domingo");
            db.insert("Client", OnConflictStrategy.IGNORE, client2);

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

            db.execSQL("Delete From Route");

            ContentValues route1 = new ContentValues();
            route1.put("clientID", 1);
            route1.put("priority", 2);
            route1.put("status", false);
            route1.put("user", 1);
            db.insert("Route", OnConflictStrategy.IGNORE, route1);

            ContentValues route2 = new ContentValues();
            route2.put("clientID", 2);
            route2.put("priority", 1);
            route2.put("status", false);
            route2.put("user", 1);
            db.insert("Route", OnConflictStrategy.IGNORE, route2);
            */


        }
    };
}
