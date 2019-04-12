package ado.edu.pucmm.rancherasystem.remote;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import ado.edu.pucmm.rancherasystem.dao.ClientDao;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Detail;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.remote.entity.CustomerEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.InvoiceEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.ItemEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.Line;
import ado.edu.pucmm.rancherasystem.remote.entity.PaymentEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.RouteEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBaseUpdater {

    private boolean processRefreshInvoicesIsRunning = false;
    private boolean processRefreshProductsIsRunning = false;
    private boolean processRefreshCustomersIsRunning = false;
    private DataSource dataSource;
    private SessionService sessionService;
    private RanchDatabaseRepo ranchDatabaseRepo;
    private Context context;

    public DataBaseUpdater() {
    }

    //aaaaaaaaaaa

    public void updatePayments(Context context){
        this.processRefreshInvoicesIsRunning = true;
        this.context = context;
        ranchDatabaseRepo = new RanchDatabaseRepo(context);
        sessionService = SessionService.getInstance(context);
        dataSource = DataSource.getInstance(context, sessionService);

        Call<List<PaymentEntity>> paymentCall = dataSource.getService().getPayments();

        paymentCall.enqueue(new Callback<List<PaymentEntity>>() {
            @Override
            public void onResponse(Call<List<PaymentEntity>> call, Response<List<PaymentEntity>> response) {
                if(response.isSuccessful()){
                    List<PaymentEntity> paymentEntities = response.body();

                    if(paymentEntities != null) {
                        ranchDatabaseRepo.erasePayments(context);
                        for(PaymentEntity payment : paymentEntities) {
                            ranchDatabaseRepo.addPaymentFromRemote(context, payment);
                        }
                    }
                }
                else{
                    Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                }
                processRefreshInvoicesIsRunning = false;
            }

            @Override
            public void onFailure(Call<List<PaymentEntity>> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                processRefreshInvoicesIsRunning = false;
            }
        });
    }

    //aaaaaaaaaaa

    public void updateRoutes(Context context){
        this.processRefreshInvoicesIsRunning = true;
        this.context = context;
        ranchDatabaseRepo = new RanchDatabaseRepo(context);
        sessionService = SessionService.getInstance(context);
        dataSource = DataSource.getInstance(context, sessionService);

        Call<List<RouteEntity>> routeCall = dataSource.getService().getRoutes();

        routeCall.enqueue(new Callback<List<RouteEntity>>() {
            @Override
            public void onResponse(Call<List<RouteEntity>> call, Response<List<RouteEntity>> response) {
                if(response.isSuccessful()){
                    List<RouteEntity> routeEntities = response.body();

                    if(routeEntities != null) {
                        ranchDatabaseRepo.eraseRoutes(context);
                        for(RouteEntity route : routeEntities) {
                            ranchDatabaseRepo.addRouteFromRemote(context, route);
                        }
                    }
                }
                else{
                    Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                }
                processRefreshInvoicesIsRunning = false;
            }

            @Override
            public void onFailure(Call<List<RouteEntity>> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                processRefreshInvoicesIsRunning = false;
            }
        });
    }

    public void updateInvoice(Context context) {
        this.processRefreshInvoicesIsRunning = true;
        this.context = context;
        ranchDatabaseRepo = new RanchDatabaseRepo(context);
        sessionService = SessionService.getInstance(context);
        dataSource = DataSource.getInstance(context, sessionService);

        Bill bill;

        Call<List<InvoiceEntity>> invoiceCall = dataSource.getService().getInvoices();

        invoiceCall.enqueue(new Callback<List<InvoiceEntity>>() {
            @Override
            public void onResponse(Call<List<InvoiceEntity>> call, Response<List<InvoiceEntity>> response) {
                if (response.isSuccessful()) {
                    ranchDatabaseRepo.eraseBills(context);
                    List<InvoiceEntity> invoiceEntities = response.body();
                    if (invoiceEntities != null) {
                        for (InvoiceEntity invoice : invoiceEntities) {
                            ranchDatabaseRepo.addBillFromRemote(context, invoice);
                        }
                    }
                } else {
                    Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                }
                processRefreshInvoicesIsRunning = false;
            }

            @Override
            public void onFailure(Call<List<InvoiceEntity>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                processRefreshInvoicesIsRunning = false;
            }
        });
    }

    public void updateProducts(Context context) {
        processRefreshProductsIsRunning = false;
        this.context = context;
        ranchDatabaseRepo = new RanchDatabaseRepo(context);
        sessionService = SessionService.getInstance(context);
        dataSource = DataSource.getInstance(context, sessionService);

        Call<List<ItemEntity>> itemCall = dataSource.getService().getItems();

        itemCall.enqueue(new Callback<List<ItemEntity>>() {
            @Override
            public void onResponse(Call<List<ItemEntity>> call, Response<List<ItemEntity>> response) {
                if (response.isSuccessful()) {
                    ranchDatabaseRepo.deleteProducts(context);
                    List<ItemEntity> itemEntities = response.body();

                    if (itemEntities != null) {
                        for (ItemEntity item : itemEntities) {
                            int idItem = Integer.valueOf(item.getId());

                            String name = item.getName();
                            if (name == null) {
                                name = "Nombre no registrado";
                            }

                            int quantity;

                            if (item.getQtyOnHand() != null) {
                                quantity = item.getQtyOnHand();
                            } else {
                                quantity = 0;
                            }

                            float price;
                            if (item.getUnitPrice() != null) {
                                price = Float.valueOf(item.getUnitPrice());
                            } else {
                                price = 0.0f;
                            }

                            String description = item.getDescription();
                            if (description == null) {
                                description = "Descripción no registrada";
                            }

                            Product product = new Product(idItem, name, quantity, price, description);
                            ranchDatabaseRepo.addProduct(context, product);

                        }
                    }
                } else {
                    Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                }
                processRefreshProductsIsRunning = false;
            }

            @Override
            public void onFailure(Call<List<ItemEntity>> call, Throwable t) {
                processRefreshProductsIsRunning = false;
            }
        });
    }

    public void updateCustomers(Context context) {
        processRefreshCustomersIsRunning = true;
        this.context = context;
        ranchDatabaseRepo = new RanchDatabaseRepo(context);
        sessionService = SessionService.getInstance(context);
        dataSource = DataSource.getInstance(context, sessionService);

        Call<List<CustomerEntity>> customerCall = dataSource.getService().getCustomers();

        customerCall.enqueue(new Callback<List<CustomerEntity>>() {
            @Override
            public void onResponse(Call<List<CustomerEntity>> call, Response<List<CustomerEntity>> response) {
                if (response.isSuccessful()) {
                    ranchDatabaseRepo.eraseAllClients(context);
                    List<CustomerEntity> customers = response.body();

                    if (customers != null) {
                        for (CustomerEntity customer : customers) {
                            String id = customer.getId();
                        /*
                        String name = customer.getGivenName();

                        if(customer.getFamilyName() != null) {
                           name = name + " " +  customer.getFamilyName();
                        }
                        */
                            String name = customer.getFullyQualifiedName();

                            String phone;
                            if (customer.getPrimaryPhone() != null) {
                                phone = customer.getPrimaryPhone().getFreeFormNumber();
                            } else {
                                phone = "Número no registrado";
                            }

                            String email;

                            if (customer.getPrimaryEmailAddr() != null) {
                                email = customer.getPrimaryEmailAddr().getAddress();
                            } else {
                                email = "Correo no registrado";
                            }

                            String address;

                            if (customer.getShipAddr() != null) {
                                address = customer.getShipAddr().getLine1(); //+ ", " + customer.getShipAddr().getCity();
                            } else {
                                address = "Dirección no registrada";
                            }

                            Client client = new Client(Integer.valueOf(id), name, phone, address, email);
                            ranchDatabaseRepo.insertClient(context, client);
                        }
                    }

                } else {
                    Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                }
                processRefreshCustomersIsRunning = false;
            }

            @Override
            public void onFailure(Call<List<CustomerEntity>> call, Throwable t) {
                // Toast.makeText(MainActivity.this, "Usuario y/o contraseña invalido", Toast.LENGTH_SHORT).show();
                processRefreshCustomersIsRunning = false;
            }
        });
    }

    public boolean isRunning(){
        return processRefreshCustomersIsRunning || processRefreshProductsIsRunning || processRefreshInvoicesIsRunning;
    }
}
