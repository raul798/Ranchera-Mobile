package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ado.edu.pucmm.rancherasystem.dao.FaqDao;
import ado.edu.pucmm.rancherasystem.dao.HelpRequestDao;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.db.RanchDb;
import ado.edu.pucmm.rancherasystem.entity.Faq;
import ado.edu.pucmm.rancherasystem.entity.HelpRequest;

public class FaqViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private FaqDao faqDao;
    private RanchDb ranchDb;
    private LiveData<List<Faq>> mAllFaqs;

    public FaqViewModel(Application application) {
        super(application);
        ranchDb = RanchDatabaseRepo.getDb(application);
        faqDao = ranchDb.getFaqDao();
        mAllFaqs = faqDao.getAllFaqs();
    }


    public void insert(Faq faq) {
        new FaqViewModel.InsertAsyncTask(faqDao).execute(faq);
    }

    public void delete() {
        new FaqViewModel.DeleteAsyncTask(faqDao).execute();
    }

    public LiveData<List<Faq>> getAllFaqs() {
        return mAllFaqs;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Faq, Void, Void> {

        FaqDao localFaqDao;

        public InsertAsyncTask(FaqDao localFaqDao) {
            this.localFaqDao = localFaqDao;
        }

        @Override
        protected Void doInBackground(Faq... faqs) {
            localFaqDao.insert(faqs[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Faq, Void, Void> {

        FaqDao localFaqDao;

        public DeleteAsyncTask(FaqDao localFaqDao) {
            this.localFaqDao = localFaqDao;
        }

        @Override
        protected Void doInBackground(Faq... faqs) {
            localFaqDao.deleteAll();
            return null;
        }
    }
}