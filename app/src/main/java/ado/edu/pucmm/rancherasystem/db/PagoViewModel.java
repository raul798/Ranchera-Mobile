package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import ado.edu.pucmm.rancherasystem.db.HelpRequest;
import ado.edu.pucmm.rancherasystem.db.HelpRequestDao;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;

public class PagoViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private PagoDao pagoDao;
    private RancheraDB rancheraDB;

    public PagoViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        pagoDao = rancheraDB.pagoDao();
    }


    public void insert(Pago pago) {
        new InsertAsyncTask(pagoDao).execute(pago);
    }

    public void delete() {
        new DeleteAsyncTask(pagoDao).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Pago, Void, Void> {

        PagoDao payDao;

        public InsertAsyncTask(PagoDao payDao) {
            this.payDao = payDao;
        }

        @Override
        protected Void doInBackground(Pago... pagos) {
            payDao.insert(pagos[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Pago, Void, Void> {

        PagoDao payDao;

        public DeleteAsyncTask(PagoDao payDao) {
            this.payDao = payDao;
        }

        @Override
        protected Void doInBackground(Pago... pagos) {
            payDao.deleteAll();
            return null;
        }
    }
}
