package ado.edu.pucmm.rancherasystem.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import ado.edu.pucmm.rancherasystem.db.Detalle;
import ado.edu.pucmm.rancherasystem.db.DetalleDao;
import ado.edu.pucmm.rancherasystem.db.Factura;
import ado.edu.pucmm.rancherasystem.db.FacturaDao;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;
import ado.edu.pucmm.rancherasystem.db.RancheraDatabaseRepo;

public class DetalleViewModel extends AndroidViewModel {
    //private String TAG = this.getClass().getSimpleName();
    private DetalleDao detalleDao;
    private RancheraDB rancheraDB;
    private RancheraDatabaseRepo repository;
    private Listener listener;

    public DetalleViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        detalleDao = rancheraDB.detalleDao();
        repository = new RancheraDatabaseRepo(application);
    }

    public void insert(Detalle detalle) {
        new InsertAsyncTask(detalleDao).execute(detalle);
    }


    private class InsertAsyncTask extends AsyncTask<Detalle, Void, Void> {

        DetalleDao detailDao;

        InsertAsyncTask(DetalleDao dao) {
            detailDao = dao;
        }

        @Override
        protected Void doInBackground(Detalle... detalles) {
            detailDao.insert(detalles[0]);
            return null;
        }
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener {
        void onFinish(Detalle detalle);
    }
}