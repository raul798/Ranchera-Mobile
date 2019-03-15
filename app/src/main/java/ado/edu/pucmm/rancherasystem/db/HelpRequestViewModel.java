package ado.edu.pucmm.rancherasystem.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import ado.edu.pucmm.rancherasystem.db.HelpRequest;
import ado.edu.pucmm.rancherasystem.db.HelpRequestDao;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;

public class HelpRequestViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private HelpRequestDao helpRequestDao;
    private RancheraDB rancheraDB;

    public HelpRequestViewModel(Application application) {
        super(application);
        rancheraDB = RancheraDB.getDatabase(application);
        helpRequestDao = rancheraDB.helpRequestDao();
    }


    public void insert(HelpRequest helpRequest) {
        new InsertAsyncTask(helpRequestDao).execute(helpRequest);
    }

    public void delete() {
        new DeleteAsyncTask(helpRequestDao).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<HelpRequest, Void, Void> {

        HelpRequestDao helpDao;

        public InsertAsyncTask(HelpRequestDao helpDao) {
            this.helpDao = helpDao;
        }

        @Override
        protected Void doInBackground(HelpRequest... helpRequests) {
            helpDao.insert(helpRequests[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<HelpRequest, Void, Void> {

        HelpRequestDao helpDao;

        public DeleteAsyncTask(HelpRequestDao helpDao) {
            this.helpDao = helpDao;
        }

        @Override
        protected Void doInBackground(HelpRequest... helpRequests) {
            helpDao.deleteAll();
            return null;
        }
    }
}
