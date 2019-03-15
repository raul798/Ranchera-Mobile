package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import ado.edu.pucmm.rancherasystem.db.Producto;
import ado.edu.pucmm.rancherasystem.db.ProductoDao;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;

public class ProductoViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private ProductoDao productoDao;
    private RancheraDB rancheraDB;

    public ProductoViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        productoDao = rancheraDB.productoDao();
    }

    public void insert(Producto producto) {
        new InsertAsyncTask(productoDao).execute(producto);
    }

    public void delete() {
        new DeleteAsyncTask(productoDao).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Producto, Void, Void> {

        ProductoDao productDao;

        public InsertAsyncTask(ProductoDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Producto... productos) {
            productDao.insert(productos[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Producto, Void, Void> {

        ProductoDao productDao;

        public DeleteAsyncTask(ProductoDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Producto... productos) {
            productDao.deleteAll();
            return null;
        }
    }


}
