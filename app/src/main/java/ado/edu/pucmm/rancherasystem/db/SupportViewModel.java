package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

public class SupportViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private SupportDao supportDao;
    private RancheraDB rancheraDB;

    public SupportViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        supportDao = rancheraDB.supportDao();
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

