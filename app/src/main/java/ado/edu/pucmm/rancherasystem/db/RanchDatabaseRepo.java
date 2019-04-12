package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.dao.BillDao;
import ado.edu.pucmm.rancherasystem.dao.ClientDao;
import ado.edu.pucmm.rancherasystem.dao.DetailDao;
import ado.edu.pucmm.rancherasystem.dao.PaymentDao;
import ado.edu.pucmm.rancherasystem.dao.ProductDao;
import ado.edu.pucmm.rancherasystem.dao.RouteDao;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Detail;
import ado.edu.pucmm.rancherasystem.entity.Payment;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.entity.Route;
import ado.edu.pucmm.rancherasystem.remote.DataSource;
import ado.edu.pucmm.rancherasystem.remote.SessionService;
import ado.edu.pucmm.rancherasystem.remote.entity.CustomerMemo;
import ado.edu.pucmm.rancherasystem.remote.entity.CustomerRef;
import ado.edu.pucmm.rancherasystem.remote.entity.InvoiceEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.ItemAccountRef;
import ado.edu.pucmm.rancherasystem.remote.entity.ItemRef;
import ado.edu.pucmm.rancherasystem.remote.entity.Line;
import ado.edu.pucmm.rancherasystem.remote.entity.LinePayment;
import ado.edu.pucmm.rancherasystem.remote.entity.LinkedTxn;
import ado.edu.pucmm.rancherasystem.remote.entity.PaymentEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.RouteEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.SalesItemLineDetail;
import ado.edu.pucmm.rancherasystem.remote.entity.Stop;
import ado.edu.pucmm.rancherasystem.remote.entity.TaxCodeRef;
import ado.edu.pucmm.rancherasystem.remote.entity.User;
import ado.edu.pucmm.rancherasystem.ui.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RanchDatabaseRepo {

    private static RanchDb ranchDb;
    private ClientDao clientDao;
    private ProductDao productDao;
    private DetailDao detailDao;
    private PaymentDao paymentDao;
    private BillDao billDao;
    private RouteDao routeDao;
    private static final Object LOCK = new Object();
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Client>> allClients;
    private List<Bill> listofbills;

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

    public void deleteProducts(Context context) {
        if (productDao == null) {
            productDao = RanchDatabaseRepo.getDb(context).getProductDao();
        }

        try {
            new DeleteProductsAsyncTask(productDao).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class DeleteProductsAsyncTask extends AsyncTask<Void, Void, Void> {

        private
        ProductDao productDao;

        DeleteProductsAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.deleteAll();
            return null;
        }
    }

    public void insertClient(Context context, Client client) {
        if (clientDao == null) {
            clientDao = RanchDatabaseRepo.getDb(context).getClientDao();
        }

        try {
           new InsertClientAsyncTask(clientDao).execute(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class InsertClientAsyncTask extends AsyncTask<Client, Void, Void> {

        private ClientDao clientDao;

        InsertClientAsyncTask(ClientDao clientDao) {
            this.clientDao = clientDao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientDao.insert(clients[0]);
            return null;
        }
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

    public List<Bill> getDoneBills(Context context, Integer client_id) {
        if (billDao == null) {
            billDao = RanchDatabaseRepo.getDb(context).getBillDao();
        }
        List<Bill> bills = new ArrayList<>();
        try {
            bills = new SelectDoneBillsAsyncTask(billDao).execute(client_id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }

    private static class SelectDoneBillsAsyncTask extends AsyncTask<Integer, Void, List<Bill>> {

        private BillDao mAsyncTaskDao;

        SelectDoneBillsAsyncTask(BillDao billdao) {
            mAsyncTaskDao = billdao;
        }

        @Override
        protected List<Bill> doInBackground(Integer... params) {
            return mAsyncTaskDao.getDoneBills(params[0]);
        }
    }

    public Integer getDoneBillCount(Context context, Integer client_id) {
        if (billDao == null) {
            billDao = RanchDatabaseRepo.getDb(context).getBillDao();
        }
        int count = 0;
        try {
            count = new SelectDoneBillCountAsyncTask(billDao).execute(client_id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    private static class SelectDoneBillCountAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private BillDao asyncTaskDao;

        SelectDoneBillCountAsyncTask(BillDao billdao) {
            asyncTaskDao = billdao;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            return asyncTaskDao.getDoneBillCount(params[0]);
        }
    }

    public Float getBillAmount(Context context, Integer payment) {
        if (paymentDao == null) {
            paymentDao = RanchDatabaseRepo.getDb(context).getPaymentDao();
        }

        Float count = 0.0f;

        try {
            count = new SelectBillAmountAsyncTask(paymentDao).execute(payment).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    private static class SelectBillAmountAsyncTask extends AsyncTask<Integer, Void, Float> {

        private PaymentDao asyncTaskDao;

        SelectBillAmountAsyncTask(PaymentDao billdao) {
            asyncTaskDao = billdao;
        }

        @Override
        protected Float doInBackground(Integer... params) {
            return asyncTaskDao.getBillAmounts(params[0]);
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

    public void addProduct(Context context, Product product) {

        productDao = productDao == null? RanchDatabaseRepo.getDb(context).getProductDao(): productDao;

        try {
            new InsertProductAsyncTask(productDao).execute(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao asyncTaskDao;

        InsertProductAsyncTask(ProductDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            asyncTaskDao.insert(products[0]);
            return null;
        }
    }

    public void deleteAllProducts() {
        if(allProducts.getValue() != null) {
            allProducts.getValue().clear();
        }
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
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

    public Integer getSelectedProductAmount(Context context, Integer product_id, Integer billId) {
        if (detailDao == null) {
            detailDao = RanchDatabaseRepo.getDb(context).getDetailDao();
        }

        Integer amount = null;

        try {
            amount = new amountAsyncTask(detailDao).execute(product_id, billId).get();
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
            return asyncTaskDao.getSelectedProductAmount(params[0], params[1]);
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

    public void eraseAllClients(Context context) {
        clientDao = clientDao == null? RanchDatabaseRepo.getDb(context).getClientDao(): clientDao;
        try {
            new deleteClientAsyncTask(clientDao).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class deleteClientAsyncTask extends AsyncTask<Void, Void, Void> {

        private ClientDao asyncTaskDao;

        deleteClientAsyncTask(ClientDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.eraseClients();
            return null;
        }
    }

    public void eraseBills(Context context) {
        billDao = billDao == null? RanchDatabaseRepo.getDb(context).getBillDao(): billDao;
        detailDao = detailDao == null? RanchDatabaseRepo.getDb(context).getDetailDao(): detailDao;

        try {
            new DeleteBillsAsyncTask(billDao,detailDao).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class DeleteBillsAsyncTask extends AsyncTask<Void, Void, Void> {

        private BillDao billDao;
        private DetailDao detailDao;

        DeleteBillsAsyncTask(BillDao billDao, DetailDao detailDao) {
            this.billDao = billDao;
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            detailDao.deleteAll();
            billDao.deleteAll();
            return null;
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

    public List<Route> getAllRoutes(Integer user) {

        if (routeDao == null) {
            routeDao = ranchDb.getRouteDao();
        }

        List<Route> routes = null;

        try {
            routes = new AllRoutesAsyncTask(routeDao).execute(user).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return routes;
    }


    private static class AllRoutesAsyncTask extends AsyncTask<Integer, Void, List<Route>>{

        RouteDao routeDao;

        public AllRoutesAsyncTask(RouteDao routeDao){this.routeDao = routeDao;}

        @Override
        protected List<Route> doInBackground(Integer... integers) {
            return routeDao.getAllRoutes(integers[0]);
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

    public void updatePaymentSignature(Context context, Integer payment_id, byte[] signature) {

        if (paymentDao == null) {
            paymentDao = RanchDatabaseRepo.getDb(context).getPaymentDao();
        }

        updateParamsSignaturePayment params = new updateParamsSignaturePayment(payment_id, signature);

        new updateSignaturePaymentAsyncTask(paymentDao).execute(params);
    }

    private static class updateParamsSignaturePayment {
        int id;
        byte[] signature;

        updateParamsSignaturePayment(int id, byte[] signature) {
            this.id = id;
            this.signature = signature;
        }
    }

    private static class updateSignaturePaymentAsyncTask extends AsyncTask<updateParamsSignaturePayment, Void, Void> {

        private PaymentDao asyncTaskDao;

        updateSignaturePaymentAsyncTask(PaymentDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateParamsSignaturePayment... params) {
            asyncTaskDao.updatePaymentSignature(params[0].id, params[0].signature);
            return null;
        }
    }
    //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA...

    private static class updateQuantityAsyncTask extends AsyncTask<updateQuantityParams, Void, Void> {

        private ProductDao asyncTaskDao;

        updateQuantityAsyncTask(ProductDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final updateQuantityParams... params) {
            asyncTaskDao.updateProductQuantity(params[0].id, params[0].quantity);
            return null;
        }
    }

    private static class updateQuantityParams {
        int id;
        int quantity;

        updateQuantityParams(int id, int quantity) {
            this.id = id;
            this.quantity = quantity;
        }
    }

    public void updateQuantity(Context context, Integer product_id, Integer quantity) {
        if (productDao == null) {
            productDao = RanchDatabaseRepo.getDb(context).getProductDao();
        }

        updateQuantityParams params = new updateQuantityParams(product_id, quantity);

        new updateQuantityAsyncTask(productDao).execute(params);
    }

    //aaaaaaaaaaaaaaa

    public void addDetail(Context context, Detail detail) {

        detailDao = detailDao == null? RanchDatabaseRepo.getDb(context).getDetailDao(): detailDao;

        try {
            new InsertDetailAsyncTask(detailDao).execute(detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class InsertDetailAsyncTask extends AsyncTask<Detail, Void, Void> {

        private DetailDao asyncTaskDao;

        InsertDetailAsyncTask(DetailDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Detail... details) {
            asyncTaskDao.insert(details[0]);
            return null;
        }
    }


    public void addBill(Context context, Bill bill) {

        billDao = billDao == null? RanchDatabaseRepo.getDb(context).getBillDao(): billDao;

        try {
            new InsertBillAsyncTask(billDao).execute(bill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRouteFromRemote(Context context, RouteEntity routeEntity){
        routeDao = routeDao == null ? RanchDatabaseRepo.getDb(context).getRouteDao(): routeDao;

        try{
            new InsertRouteEntityTask(routeDao).execute(routeEntity);
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }


    private static class InsertRouteEntityTask extends AsyncTask<RouteEntity, Void, Void> {
        private RouteDao routeDao;

        public InsertRouteEntityTask(RouteDao routeDao) {
            this.routeDao = routeDao;
        }

        @Override
        protected Void doInBackground(RouteEntity... routeEntities) {

            int user = routeEntities[0].getUser();
            List<Stop> stops = routeEntities[0].getStops();

            for (Stop stop : stops) {
                int stopPriority = stop.getStopPriority();
                int clientId = stop.getClientId();

                //se van a volver a poner false cuando se borre
                Route route = new Route(clientId, stopPriority, false, user);
                routeDao.insert(route);
            }

            return null;
        }
    }

    public void addBillFromRemote(Context context, InvoiceEntity entity) {

        billDao = billDao == null? RanchDatabaseRepo.getDb(context).getBillDao(): billDao;
        detailDao = detailDao == null? RanchDatabaseRepo.getDb(context).getDetailDao(): detailDao;
        productDao = productDao == null? RanchDatabaseRepo.getDb(context).getProductDao(): productDao;

        try {
            new InsertInvoiceEntityAsyncTask(billDao, detailDao, productDao).execute(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class InsertInvoiceEntityAsyncTask extends AsyncTask<InvoiceEntity, Void, Void> {

        private BillDao billDao;
        private ProductDao productDao;
        private DetailDao asyncTaskDao;

        InsertInvoiceEntityAsyncTask(BillDao dao, DetailDao detailDao, ProductDao itemDao) {
            billDao = dao;
            asyncTaskDao =  detailDao;
            productDao = itemDao;
        }

        @Override
        protected Void doInBackground(InvoiceEntity... InvoiceEntity) {
            InvoiceEntity  invoice = InvoiceEntity[0];
            String id = invoice.getId();

            int idInvoice = id == null ? -1 : Integer.valueOf(id);

            String client = invoice.getCustomerRef().getValue();

            int clientId = client == null ? -1 : Integer.valueOf(client);

            //revisar
            String description = "Done";


            if (idInvoice != -1 && clientId != -1) {
                Bill bill = new Bill(idInvoice, clientId, description); //lo creo aqui
                bill.setId(billDao.insert(bill).intValue());
                List<Line> lines = invoice.getLineList();

                for (Line line : lines) {

                    SalesItemLineDetail details = line.getSalesItemLineDetail();

                    String product = details == null ? null : details.getItemRef().getValue();

                    int idProduct = product == null ? -1 : Integer.valueOf(product);

                    float quantity = details == null ? -1 : details.getQty();

                    if (idProduct != -1 && quantity != -1) {
                        Detail detail = new Detail(bill.getId(), idProduct, (int) quantity);
                        Product p = productDao.searchProductByID(idProduct);
                        float total = p != null? p.getPrice():0;
                        billDao.updateBillTotal(bill.getId(),total);
                        asyncTaskDao.insert(detail);
                    }
                }
            }
            return null;
        }
    }

    public void sendBills(Context context, DataSource dataSource){
        billDao = billDao == null? RanchDatabaseRepo.getDb(context).getBillDao(): billDao;
        detailDao = detailDao == null? RanchDatabaseRepo.getDb(context).getDetailDao(): detailDao;
        productDao = productDao == null ? RanchDatabaseRepo.getDb(context).getProductDao():productDao;

        List<Bill> bills = null;
        try {
            new SendBillsAsyncTask(billDao, detailDao, productDao).execute(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected static class SendBillsAsyncTask extends AsyncTask<DataSource, Void, Void>{

        private BillDao billDao;
        private DetailDao detailDao;
        private ProductDao productDao;

        public SendBillsAsyncTask(BillDao billDao, DetailDao detailDao, ProductDao productDao) {
            this.billDao = billDao;
            this.detailDao = detailDao;
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(DataSource... voids) {
            List<Bill> bills = billDao.getAllBills();

            for(Bill bill : bills){
                List<Detail> details = detailDao.getBillDetails(bill.getId());
                List<Line> lines = new ArrayList<>();
                int extid = bill.getExternalId();
                if(extid == -1 && details != null) {
                    for (Detail detail : details) {
                        Product product = productDao.searchProductByID(detail.getProduct());
                        ItemRef itemRef = new ItemRef(String.valueOf(product.getId()));
                        float quantity = detail.getQuantity();
                        ItemAccountRef itemAccountRef = new ItemAccountRef("79");
                        TaxCodeRef taxCodeRef = new TaxCodeRef("TAX");
                        SalesItemLineDetail salesItemLineDetail = new SalesItemLineDetail(itemRef, product.getQuantity(), itemAccountRef, taxCodeRef);
                        Line line = new Line(detail.getId(), bill.getDescription(), quantity, "SALES_ITEM_LINE_DETAIL", salesItemLineDetail);
                        lines.add(line);

                    }
                    CustomerRef customerRef = new CustomerRef(String.valueOf(bill.getClient()));
                    CustomerMemo customerMemo = new CustomerMemo("Test");
                    InvoiceEntity invoiceEntity = new InvoiceEntity(String.valueOf(bill.getId()), lines, customerRef, customerMemo);
                    Call<InvoiceEntity> invoiceCall = voids[0].getService().sendInvoice(invoiceEntity);
                    invoiceCall.enqueue(new Callback<InvoiceEntity>() {
                                            @Override
                                            public void onResponse(Call<InvoiceEntity> call, Response<InvoiceEntity> response) {
                                                if(response.isSuccessful()){
                                                    System.out.println("wii");
                                                    //Toast.makeTe, "Bienvenido", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    System.out.println("buu");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<InvoiceEntity> call, Throwable throwable) {
                                                throwable.printStackTrace();
                                            }
                                        }

                    );
                }

            }
            //billDao.deleteAll();
            return null;
        }
    }

    public void eraseRoutes(Context context){
        routeDao = routeDao == null ? RanchDatabaseRepo.getDb(context).getRouteDao(): routeDao;
        try{
            new EraseRoutesAsyncTast(routeDao);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class EraseRoutesAsyncTast extends AsyncTask<Void,Void,Void>{
        private RouteDao routeDao;

        public EraseRoutesAsyncTast(RouteDao routeDao) {
            this.routeDao = routeDao;
        }

        @Override
        protected Void doInBackground(Void... routes) {
            routeDao.deleteAll();
            return null;
        }
    }


    private static class InsertBillAsyncTask extends AsyncTask<Bill, Void, Void> {

        private BillDao asyncTaskDao;

        InsertBillAsyncTask(BillDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Bill... bills) {
            bills[0].setId(asyncTaskDao.insert(bills[0]).intValue());
            return null;
        }
    }

    public void erasePayments(Context context){
        paymentDao = paymentDao == null ? RanchDatabaseRepo.getDb(context).getPaymentDao(): paymentDao;
        try{
            new ErasePaymentsAsyncTast(paymentDao);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ErasePaymentsAsyncTast extends AsyncTask<Void,Void,Void>{
        private PaymentDao paymentDao;

        public ErasePaymentsAsyncTast(PaymentDao paymentDao) {
            this.paymentDao = paymentDao;
        }

        @Override
        protected Void doInBackground(Void... payments) {
            paymentDao.deleteAll();
            return null;
        }
    }

    public void addPaymentFromRemote(Context context, PaymentEntity paymentEntity){
        paymentDao = paymentDao == null ? RanchDatabaseRepo.getDb(context).getPaymentDao(): paymentDao;

        try{
            new InsertPaymentEntityTask(paymentDao).execute(paymentEntity);
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }


    private static class InsertPaymentEntityTask extends AsyncTask<PaymentEntity, Void, Void> {
        private PaymentDao paymentDao;

        public InsertPaymentEntityTask(PaymentDao paymentDao) {
            this.paymentDao = paymentDao;
        }

        @Override
        protected Void doInBackground(PaymentEntity... paymentEntities) {

            float amount = paymentEntities[0].getTotalAmt();
            int client = Integer.valueOf(paymentEntities[0].getCustomerRef().getValue());
            //int bill = Integer.valueOf(paymentEntities[0].getLine().get(0).getLinkedTxn().get(0).getTxnId());
            List<LinePayment> linePayments = paymentEntities[0].getLine();
            int bill = 0;
            for (LinePayment linePayment : linePayments)
            {
                List<LinkedTxn> linkedTxns = linePayment.getLinkedTxn();
                for (LinkedTxn linkedTxn : linkedTxns)
                {
                    bill = Integer.valueOf(linkedTxn.getTxnId());
                }
            }
            Payment payment = new Payment(amount, bill, client);
            paymentDao.insert(payment);
            return null;
        }
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {
        }

        public void onOpen(SupportSQLiteDatabase db) {

            //delete existing data
            //db.execSQL("Delete From Client");

//            ContentValues client1 = new ContentValues();
//            client1.put("name", "Raul Test");
//            client1.put("phoneNumber", "809-123-4567");
//            client1.put("email", "raul.test@email.com");
//            client1.put("address", "Calle Sanabacoa, Santo Domingo");
//            db.insert("Client", OnConflictStrategy.ABORT, client1);
//
//            ContentValues client2 = new ContentValues();
//            client2.put("name", "Dante Fana");
//            client2.put("phoneNumber", "829-123-4567");
//            client2.put("email", "dante.test@email.com");
//            client2.put("address", "Calle Sanabacoa, Santo Domingo");
//            db.insert("Client", OnConflictStrategy.ABORT, client2);
//
//            //db.execSQL("Delete From Product");
//
//            ContentValues contentValuesProduct = new ContentValues();
//            contentValuesProduct.put("name", "Product#0001");
//            contentValuesProduct.put("quantity", 40);
//            contentValuesProduct.put("price", 500);
//            contentValuesProduct.put("description", "Producto de testing 1");
//            db.insert("Product", OnConflictStrategy.ABORT, contentValuesProduct);
//
//            contentValuesProduct = new ContentValues();
//            contentValuesProduct.put("name", "Product#0002");
//            contentValuesProduct.put("quantity", 30);
//            contentValuesProduct.put("price", 1000);
//            contentValuesProduct.put("description", "Producto de testing 2");
//            db.insert("Product", OnConflictStrategy.ABORT, contentValuesProduct);
//
//            //db.execSQL("Delete From Route");
//
//            ContentValues route1 = new ContentValues();
//            route1.put("clientID", 1);
//            route1.put("priority", 2);
//            route1.put("status", false);
//            route1.put("user", 1);
//            db.insert("Route", OnConflictStrategy.ABORT, route1);
//
//            ContentValues route2 = new ContentValues();
//            route2.put("clientID", 2);
//            route2.put("priority", 1);
//            route2.put("status", false);
//            route2.put("user", 1);
//            db.insert("Route", OnConflictStrategy.ABORT, route2);

        }
    };
}
