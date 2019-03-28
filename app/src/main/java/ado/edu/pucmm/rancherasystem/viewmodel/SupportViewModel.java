package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import ado.edu.pucmm.rancherasystem.dao.SupportDao;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.db.RanchDb;
import ado.edu.pucmm.rancherasystem.entity.Support;

public class SupportViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private SupportDao supportDao;
    private RanchDb ranchDb;

    public SupportViewModel(Application application) {
        super(application);
        ranchDb = RanchDatabaseRepo.getDb(application);
        supportDao = ranchDb.getSupportDao();
    }


    public void insert(Support support) {
        new InsertAsyncTask(supportDao).execute(support);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Support, Void, Void> {

        SupportDao questionDao;

        public InsertAsyncTask(SupportDao questionDao) {
            this.questionDao = questionDao;
        }

        @Override
        protected Void doInBackground(Support... supports) {
            questionDao.insert(supports[0]);
            return null;
        }
    }
}

