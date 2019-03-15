package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class DetalleViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private DetalleDao detalleDao;
    private RancheraDB rancheraDB;
    private LiveData<List<Detalle>> mAllDetalles;

    public DetalleViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        detalleDao = rancheraDB.detalleDao();
        mAllDetalles = detalleDao.getAllDetalles();
    }


    public LiveData<List<Detalle>> getAllDetalles() {
        return mAllDetalles;
    }

    public void insert(Detalle detalle) {
        new InsertAsyncTask(detalleDao).execute(detalle);
    }

    public void delete() {
        new DeleteAsyncTask(detalleDao).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Detalle, Void, Void> {

        DetalleDao detailDao;

        public InsertAsyncTask(DetalleDao detailDao) {
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Detalle... detalles) {
            detailDao.insert(detalles[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Detalle, Void, Void> {

        DetalleDao detailDao;

        public DeleteAsyncTask(DetalleDao detailDao) {
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Detalle... detalles) {
            detailDao.deleteAll();
            return null;
        }
    }
}
