package ado.edu.pucmm.rancherasystem.remote;

import android.content.Context;
import android.content.Intent;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
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
import ado.edu.pucmm.rancherasystem.remote.entity.SalesItemLineDetail;
import ado.edu.pucmm.rancherasystem.remote.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBaseUpdater {

    private boolean processRefreshInvoicesIsRunning = false;
    private boolean processRefreshProductsIsRunning = false;
    private boolean processRefreshCustomersIsRunning = false;

    public interface Listener{
        void onFinish();
    }

    private Listener listener;
    private DataSource dataSource;
    private SessionService sessionService;
    private RanchDatabaseRepo ranchDatabaseRepo;
    private Context context;

    public DataBaseUpdater(){}

    public void setListener(Listener listener){
     this.listener = listener;
    }

    public void updateInvoice(Context context){
        this.processRefreshInvoicesIsRunning = true;
        this.context = context;
        ranchDatabaseRepo = new RanchDatabaseRepo(context);
        sessionService = SessionService.getInstance(context);
        dataSource = DataSource.getInstance(context, sessionService);

        Call<List<InvoiceEntity>> invoiceCall = dataSource.getService().getInvoices();

        invoiceCall.enqueue(new Callback<List<InvoiceEntity>>(){
            @Override
            public void onResponse(Call<List<InvoiceEntity>> call, Response<List<InvoiceEntity>> response) {
                if(response.isSuccessful()){
                    ranchDatabaseRepo.eraseBills(context);

                    List<InvoiceEntity> invoiceEntities = response.body();

                    if(invoiceEntities != null){

                        for(InvoiceEntity invoice : invoiceEntities){

                            String id = invoice.getId();

                            int idInvoice = id == null ? -1 : Integer.valueOf(id);

                            String client = invoice.getCustomerRef().getValue();

                            int clientId = client == null ? -1 : Integer.valueOf(client);

                            //revisar
                            String description = "Done";


                            if(idInvoice != -1 && clientId != -1) {
                                Bill bill = new Bill(idInvoice, clientId, description);
                                List<Line> lines = invoice.getLineList();

                                for (Line line : lines) {

                                    SalesItemLineDetail details = line.getSalesItemLineDetail();

                                    String product = details == null ? null : details.getItemRef().getValue();

                                    int idProduct = product == null ? -1 : Integer.valueOf(product);

                                    float quantity = details == null ? -1 : details.getQty();

                                    if(idProduct != -1 && quantity != -1) {
                                        Detail detail = new Detail(bill.getId(), idProduct, (int)quantity);
                                        ranchDatabaseRepo.addBill(context, bill);
                                        ranchDatabaseRepo.addDetail(context, detail);
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                }
                processRefreshInvoicesIsRunning = false;
                listener.onFinish(); // llama eso en el ultimo proceso que llames asi se marcara como terminado
            }

            @Override
            public void onFailure(Call<List<InvoiceEntity>> call, Throwable t) {
                listener.onFinish(); // llama eso en el ultimo proceso que llames asi se marcara como terminado
                t.printStackTrace();
                Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
                processRefreshInvoicesIsRunning = false;
            }
        });
    }

    public void updateProducts(Context context){
        processRefreshInvoicesIsRunning = false;
        this.context = context;
     ranchDatabaseRepo = new RanchDatabaseRepo(context);
     sessionService = SessionService.getInstance(context);
     dataSource = DataSource.getInstance(context, sessionService);

     Call<List<ItemEntity>> itemCall = dataSource.getService().getItems();

     itemCall.enqueue(new Callback<List<ItemEntity>>(){
         @Override
         public void onResponse(Call<List<ItemEntity>> call, Response<List<ItemEntity>> response) {
             if(response.isSuccessful()){
                 ranchDatabaseRepo.deleteProducts(context);
                 List<ItemEntity> itemEntities = response.body();

                 if(itemEntities != null){
                     for(ItemEntity item : itemEntities){
                          int idItem = Integer.valueOf(item.getId());

                          String name = item.getName();
                          if(name == null) {
                              name = "Nombre no registrado";
                          }

                          int quantity;

                          if(item.getQtyOnHand() != null){
                              quantity = item.getQtyOnHand();
                          }
                          else{
                              quantity = 0;
                          }

                          float price;
                          if(item.getUnitPrice() != null){
                              price = Float.valueOf(item.getUnitPrice());
                          }
                          else{
                              price = 0.0f;
                          }

                          String description = item.getDescription();
                          if(description == null){
                              description = "Descripción no registrada";
                          }

                          Product product = new Product(idItem,name,quantity,price,description);
                          ranchDatabaseRepo.addProduct(context, product);

                     }
                 }
             }
             else{
                 Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(Call<List<ItemEntity>> call, Throwable t) {

         }
     });
    }

    public void updateCustomers(Context context){

     this.context = context;
     ranchDatabaseRepo = new RanchDatabaseRepo(context);
     sessionService = SessionService.getInstance(context);
     dataSource = DataSource.getInstance(context, sessionService);

     Call<List<CustomerEntity>> customerCall = dataSource.getService().getCustomers();

     customerCall.enqueue(new Callback<List<CustomerEntity>>() {
         @Override
         public void onResponse(Call<List<CustomerEntity>> call, Response<List<CustomerEntity>> response) {
             if(response.isSuccessful()) {
                ranchDatabaseRepo.eraseAllClients(context);
                 List<CustomerEntity> customers = response.body();

                if(customers != null) {
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
                        if(customer.getPrimaryPhone() != null) {
                            phone = customer.getPrimaryPhone().getFreeFormNumber();
                        }
                        else{
                            phone = "Número no registrado";
                        }

                        String email;

                        if(customer.getPrimaryEmailAddr() != null) {
                            email = customer.getPrimaryEmailAddr().getAddress();
                        }
                        else{
                            email = "Correo no registrado";
                        }

                        String address;

                        if(customer.getShipAddr() != null) {
                            address = customer.getShipAddr().getLine1() + ", " + customer.getShipAddr().getCity();
                        }
                        else{
                            address = "Dirección no registrada";
                        }

                        Client client = new Client(Integer.valueOf(id), name, phone, address, email);
                        ranchDatabaseRepo.insertClient(context, client);
                    }
                }

             }
             else{
                 Toast.makeText(context, "Sincronizacion fallida", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(Call<List<CustomerEntity>> call, Throwable t) {
            // Toast.makeText(MainActivity.this, "Usuario y/o contraseña invalido", Toast.LENGTH_SHORT).show();

         }
     });
    }
}
