package ado.edu.pucmm.rancherasystem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RancheraViewModel extends AndroidViewModel {
    private RancheraRepository repository;
    private LiveData<List<Client>> allClients;

    public RancheraViewModel (Application application) {
        super(application);
        repository = new RancheraRepository(application);
        allClients = repository.getAllClients();
    }

    LiveData<List<Client>> getAllClients() { return allClients; }
}
