package ado.edu.pucmm.rancherasystem.remote;

import java.util.List;

import ado.edu.pucmm.rancherasystem.remote.entity.CustomerEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.InvoiceEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.ItemEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.PaymentEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.RouteEntity;
import ado.edu.pucmm.rancherasystem.remote.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @POST("/login")
    Call<User> verify(@Body User user);

    @GET("/protected/invoices/")
    Call<List<InvoiceEntity>> getInvoices();

    @GET("/protected/customers/")
    Call<List<CustomerEntity>> getCustomers();

    @GET("/protected/items/")
    Call<List<ItemEntity>> getItems();

    @GET("/protected/routes/")
    Call<List<RouteEntity>> getRoutes();

    @GET("/protected/payments/")
    Call<List<PaymentEntity>> getPayments();
}
