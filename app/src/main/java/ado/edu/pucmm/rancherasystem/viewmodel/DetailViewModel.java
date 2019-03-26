package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Detail;
import ado.edu.pucmm.rancherasystem.dao.DetailDao;
import ado.edu.pucmm.rancherasystem.db.RanchDb;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;

public class DetailViewModel extends AndroidViewModel {
    private DetailDao detailDao;
    private RanchDb ranchDb;
    private RanchDatabaseRepo repository;
    private LiveData<List<Detail>> mAllDetalles;

    public DetailViewModel(Application application) {
        super(application);
        ranchDb = RanchDatabaseRepo.getDb(application);
        detailDao = ranchDb.getDetailDao();
        repository = new RanchDatabaseRepo(application);
    }

    public LiveData<List<Detail>> getAllDetails() {
        return mAllDetalles;
    }

    public void delete() {
        new DeleteAsyncTask(detailDao).execute();
    }

    public void insert(Detail detail) {
        new InsertAsyncTask(detailDao).execute(detail);
    }


    private class InsertAsyncTask extends AsyncTask<Detail, Void, Void> {

        DetailDao detailDao;

        public InsertAsyncTask(DetailDao detailDao) {
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Detail... details) {
            detailDao.insert(details[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Detail, Void, Void> {

        DetailDao detailDao;

        public DeleteAsyncTask(DetailDao detailDao) {
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Detail... details) {
            detailDao.deleteAll();
            return null;
        }
    }
}