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

    public synchronized static RancheraDB getRancheraDB(Context context){
        if(rancheraDB == null) {
            synchronized (LOCK) {
                if (rancheraDB == null) {
                    rancheraDB = Room.databaseBuilder(context,
                            RancheraDB.class, "ranchera db")
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
    }
}
