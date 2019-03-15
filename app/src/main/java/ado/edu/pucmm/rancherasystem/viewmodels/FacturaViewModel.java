package ado.edu.pucmm.rancherasystem.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import ado.edu.pucmm.rancherasystem.db.Factura;
import ado.edu.pucmm.rancherasystem.db.FacturaDao;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;
import ado.edu.pucmm.rancherasystem.db.RancheraDatabaseRepo;

public class FacturaViewModel extends AndroidViewModel {
    //private String TAG = this.getClass().getSimpleName();
    private FacturaDao facturaDao;
    private RancheraDB rancheraDB;
    private RancheraDatabaseRepo repository;
    private Listener listener;

    public FacturaViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        facturaDao = rancheraDB.facturaDao();
        repository = new RancheraDatabaseRepo(application);
    }

    public void insert(Factura factura) {
        new InsertAsyncTask(facturaDao).execute(factura);
    }


    private class InsertAsyncTask extends AsyncTask<Factura, Void, Void> {

        FacturaDao billDao;

        InsertAsyncTask(FacturaDao dao) {
            billDao = dao;
        }

        @Override
        protected Void doInBackground(Factura... facturas) {
            int id = billDao.insert(facturas[0]).intValue();
            facturas[0].setId(id);
            listener.onFinish(facturas[0]);
            return null;
        }
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener {
        void onFinish(Factura factura);
    }
}