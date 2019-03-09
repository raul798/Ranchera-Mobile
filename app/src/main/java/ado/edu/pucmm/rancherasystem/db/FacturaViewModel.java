package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

public class FacturaViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private FacturaDao facturaDao;
    private RancheraDB rancheraDB;

    public FacturaViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        facturaDao = rancheraDB.facturaDao();
    }

    public void insert(Factura factura) {
        new InsertAsyncTask(facturaDao).execute(factura);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Factura, Void, Void> {

        FacturaDao checkDao;

        public InsertAsyncTask(FacturaDao checkDao) {
            this.checkDao = checkDao;
        }

        @Override
        protected Void doInBackground(Factura... facturas) {
            checkDao.insert(facturas[0]);
            return null;
        }
    }
}