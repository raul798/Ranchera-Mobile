package ado.edu.pucmm.rancherasystem.remote;

import android.content.Context;
import android.content.Intent;
import android.se.omapi.Session;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.dao.ClientDao;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.remote.entity.CustomerEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBaseUpdater {

     private DataSource dataSource;
     private SessionService sessionService;
     private RanchDatabaseRepo ranchDatabaseRepo;
     private Context context;

     public DataBaseUpdater(){

     }

     public void updateCustomers(Context context){

         this.context = context;
         ranchDatabaseRepo = new RanchDatabaseRepo(context);
         sessionService = SessionService.getInstance(context);
         dataSource = DataSource.getInstance(context, sessionService);

         List<CustomerEntity> customerList = new ArrayList<CustomerEntity>();

         Call<List<CustomerEntity>> customerCall = dataSource.getService().getCustomers();

         customerCall.enqueue(new Callback<List<CustomerEntity>>() {
             @Override
             public void onResponse(Call<List<CustomerEntity>> call, Response<List<CustomerEntity>> response) {
                 if(response.isSuccessful()) {
                     ClientDao clientDao = RanchDatabaseRepo.getDb(context).getClientDao();
                     clientDao.deleteAll();
                     List<CustomerEntity> customers = response.body();

                     for(CustomerEntity customer : customers){
                         int id = customer.getIdCustomer();
                         String name = customer.getGivenName() + " " + customer.getFamilyName();
                         String phone = customer.getPrimaryPhone().getFreeFormNumber();
                         String email = customer.getPrimaryEmailAddr().getAddress();
                         String address = customer.getShipAddr().getLine1() + ", " + customer.getShipAddr().getCity();
                         Client client = new Client(id, name, phone, address, email);
                         clientDao.insert(client);
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
