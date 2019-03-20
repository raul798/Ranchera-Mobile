package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import ado.edu.pucmm.rancherasystem.dao.BillDao;
import ado.edu.pucmm.rancherasystem.dao.ClientDao;
import ado.edu.pucmm.rancherasystem.dao.DetailDao;
import ado.edu.pucmm.rancherasystem.dao.HelpRequestDao;
import ado.edu.pucmm.rancherasystem.dao.PaymentDao;
import ado.edu.pucmm.rancherasystem.dao.ProductDao;
import ado.edu.pucmm.rancherasystem.dao.SupportDao;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Detail;
import ado.edu.pucmm.rancherasystem.entity.HelpRequest;
import ado.edu.pucmm.rancherasystem.entity.Payment;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.entity.Support;


@Database(entities = {
        Client.class, Bill.class,
        Product.class, Detail.class,
        Payment.class, HelpRequest.class,
        Support.class
}, version = 1)
public abstract class RanchDb extends RoomDatabase {

    public abstract ClientDao getClientDao();
    public abstract ProductDao getProductDao();
    public abstract DetailDao getDetailDao();
    public abstract BillDao getBillDao();
    public abstract PaymentDao getPaymentDao();
    public abstract HelpRequestDao getHelpRequestDao();
    public abstract SupportDao getSupportDao();

    private static volatile RanchDb INSTANCE;

    public static RanchDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RanchDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RanchDb.class, "ranchera_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BillDao mDao;

        PopulateDbAsync(RanchDb db) {
            mDao = db.getBillDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }

}