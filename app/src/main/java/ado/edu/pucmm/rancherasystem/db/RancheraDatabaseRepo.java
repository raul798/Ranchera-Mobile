package ado.edu.pucmm.rancherasystem.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class RancheraDatabaseRepo {

    private static RancheraDB rancheraDB;
    private ClientDao clientDao;
    private BillDao billDao;
    private LiveData<List<Bill>> listofbills;
    private static final Object LOCK = new Object();

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        public void onCreate (SupportSQLiteDatabase db){

        }
        public void onOpen (SupportSQLiteDatabase db){
            //delete existing data
            db.execSQL("Delete From Client");

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "eTest");
            contentValues.put("phoneNumber", "809-123-4567");
            contentValues.put("email", "test@email.com");
            contentValues.put("address", "Test address #10");
            db.insert("Client", OnConflictStrategy.IGNORE, contentValues);

            contentValues = new ContentValues();
            contentValues.put("id", "001");
            contentValues.put("debt","100000");
            contentValues.put("description","first bill test");
            contentValues.put("client_id", "1");
            db.insert("bill_table",OnConflictStrategy.IGNORE,contentValues );
        }
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
        new insertAsyncTask(billDao).execute(bill);
    }

    private static class insertAsyncTask extends AsyncTask<Bill, Void, Void> {

        private BillDao mAsyncTaskDao;

        insertAsyncTask(BillDao billdao) {
            mAsyncTaskDao = billdao;
        }

        @Override
        protected Void doInBackground(final Bill... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    public List<Client> getClient(Context context, String clientStr) {
        if (clientDao == null) {
            clientDao = RancheraDatabaseRepo.getRancheraDB(context).clientDao();
        }
        return clientDao.getClients(clientStr+"%");
    }
    public List<Bill> getBills(Context context, String billStr) {
        if (billDao == null) {
            billDao = RancheraDatabaseRepo.getRancheraDB(context).billDao();
        }
        return billDao.getBills(billStr+"%");
    }

    public LiveData<List<Bill>> getListofbills() {
        return listofbills;
    }


}
