package ado.edu.pucmm.rancherasystem.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Product;
import ado.edu.pucmm.rancherasystem.db.RancheraDatabaseRepo;

public class ProductViewModel extends AndroidViewModel {

    private RancheraDatabaseRepo repository;

    private LiveData<List<Product>> products;

    public ProductViewModel (Application application) {
        super(application);
        repository = new RancheraDatabaseRepo(application);
        products = repository.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() { return products; }

    public void insert(Product product) { repository.insert(product); }
}