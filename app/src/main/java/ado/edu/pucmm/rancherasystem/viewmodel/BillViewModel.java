package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import ado.edu.pucmm.rancherasystem.dao.BillDao;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.db.RanchDb;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;

public class BillViewModel extends AndroidViewModel {
    private BillDao billDao;
    private RanchDb ranchDb;
    private RanchDatabaseRepo repository;
    private Listener listener;

    public BillViewModel(Application application) {
        super(application);
        ranchDb = RanchDatabaseRepo.getDb(application);
        billDao = ranchDb.getBillDao();
        repository = new RanchDatabaseRepo(application);
    }

    public void insert(Bill bill) {
        new InsertAsyncTask(billDao).execute(bill);
    }


    private class InsertAsyncTask extends AsyncTask<Bill, Void, Void> {

        BillDao billDao;

        InsertAsyncTask(BillDao dao) {
            billDao = dao;
        }

        @Override
        protected Void doInBackground(Bill... bills) {

            int id = billDao.insert(bills[0]).intValue();
            bills[0].setId(id);
            listener.onFinish(bills[0]);
            return null;
        }
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener {
        void onFinish(Bill bill);
    }

    public void delete() {
        new DeleteAsyncTask(billDao).execute();
    }


    private class DeleteAsyncTask extends AsyncTask<Bill, Void, Void> {

        BillDao billDao;

        public DeleteAsyncTask(BillDao billDao) {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Bill...bills) {
            billDao.deleteAll();
            return null;
        }
    }
}