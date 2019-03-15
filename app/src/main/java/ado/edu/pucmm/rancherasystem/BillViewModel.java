package ado.edu.pucmm.rancherasystem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Bill;
import ado.edu.pucmm.rancherasystem.db.RancheraDatabaseRepo;

public class BillViewModel extends AndroidViewModel {

    private RancheraDatabaseRepo mRepository;
    private LiveData<List<Bill>> mAllBills;

    public BillViewModel (Application application){
        super(application);
        mRepository = new RancheraDatabaseRepo(application);
    }

    public LiveData<List<Bill>> getmAllBills() {
        return mAllBills;
    }

    public void insert(Bill bill){
        mRepository.insert(bill);
    }
}
