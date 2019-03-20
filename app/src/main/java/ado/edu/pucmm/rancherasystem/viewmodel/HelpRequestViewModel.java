package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import ado.edu.pucmm.rancherasystem.dao.HelpRequestDao;
import ado.edu.pucmm.rancherasystem.entity.HelpRequest;
import ado.edu.pucmm.rancherasystem.db.RanchDb;

public class HelpRequestViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private HelpRequestDao helpRequestDao;
    private RanchDb ranchDb;

    public HelpRequestViewModel(Application application) {
        super(application);
        ranchDb = RanchDb.getDatabase(application);
        helpRequestDao = ranchDb.getHelpRequestDao();
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
