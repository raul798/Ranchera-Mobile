package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ado.edu.pucmm.rancherasystem.dao.BillDao;
import ado.edu.pucmm.rancherasystem.dao.ClientDao;
import ado.edu.pucmm.rancherasystem.dao.DetailDao;
import ado.edu.pucmm.rancherasystem.dao.HelpRequestDao;
import ado.edu.pucmm.rancherasystem.dao.PaymentDao;
import ado.edu.pucmm.rancherasystem.dao.ProductDao;
import ado.edu.pucmm.rancherasystem.dao.RouteDao;
import ado.edu.pucmm.rancherasystem.dao.FaqDao;
import ado.edu.pucmm.rancherasystem.dao.SupportDao;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Detail;
import ado.edu.pucmm.rancherasystem.entity.HelpRequest;
import ado.edu.pucmm.rancherasystem.entity.Payment;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.entity.Route;
import ado.edu.pucmm.rancherasystem.entity.Faq;
import ado.edu.pucmm.rancherasystem.entity.Support;


@Database(entities = {
        Client.class, Bill.class,
        Product.class, Detail.class,
        Payment.class, HelpRequest.class,
        Support.class, Route.class,
        Faq.class
}, version = 1)
public abstract class RanchDb extends RoomDatabase {

    public abstract ClientDao getClientDao();
    public abstract ProductDao getProductDao();
    public abstract DetailDao getDetailDao();
    public abstract BillDao getBillDao();
    public abstract PaymentDao getPaymentDao();
    public abstract HelpRequestDao getHelpRequestDao();
    public abstract SupportDao getSupportDao();
    public abstract RouteDao getRouteDao();
    public abstract FaqDao getFaqDao();
}