package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ado.edu.pucmm.rancherasystem.dao.ProductDao;
import ado.edu.pucmm.rancherasystem.db.RanchDb;
import ado.edu.pucmm.rancherasystem.entity.Product;

public class ProductViewModel extends AndroidViewModel {

    private String TAG = ProductViewModel.class.getSimpleName();
    private ProductDao productDao;
    private RanchDb ranchDb;
    private LiveData<List<Product>> products;

    public ProductViewModel(Application application) {
        super(application);
        ranchDb = RanchDb.getDatabase(application);
        productDao = ranchDb.getProductDao();
        products = productDao.getAllProducts();
    }

    public void insert(Product product) {
        new InsertAsyncTask(productDao).execute(product);
    }

    public void delete() {
        new DeleteAsyncTask(productDao).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Product, Void, Void> {

        ProductDao productDao;

        public InsertAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product...products) {
            productDao.insert(products[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Product, Void, Void> {

        ProductDao productDao;

        public DeleteAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... productos) {
            productDao.deleteAll();
            return null;
        }
    }

    public LiveData<List<Product>> getAllProducts() { return products; }



}
