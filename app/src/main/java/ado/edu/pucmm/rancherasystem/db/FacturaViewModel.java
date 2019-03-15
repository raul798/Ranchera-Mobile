package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class FacturaViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private FacturaDao facturaDao;
    private RancheraDB rancheraDB;
    private LiveData<List<Factura>> mAllFacturas;

    public FacturaViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        facturaDao = rancheraDB.facturaDao();
        mAllFacturas = facturaDao.getAllFacturas();
    }


    public LiveData<List<Factura>> getAllFacturas() {
        return mAllFacturas;
    }

    public void insert(Factura factura) {
        new InsertAsyncTask(facturaDao).execute(factura);
    }

    public void delete() {
        new DeleteAsyncTask(facturaDao).execute();
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

    private class DeleteAsyncTask extends AsyncTask<Factura, Void, Void> {

        FacturaDao checkDao;

        public DeleteAsyncTask(FacturaDao checkDao) {
            this.checkDao = checkDao;
        }

        @Override
        protected Void doInBackground(Factura... facturas) {
            checkDao.deleteAll();
            return null;
        }
    }
}
