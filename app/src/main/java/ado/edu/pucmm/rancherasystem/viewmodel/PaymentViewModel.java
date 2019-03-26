package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import ado.edu.pucmm.rancherasystem.dao.PaymentDao;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Payment;
import ado.edu.pucmm.rancherasystem.db.RanchDb;

public class PaymentViewModel extends AndroidViewModel {

    private String TAG = PaymentViewModel.class.getSimpleName();
    private PaymentDao paymentDao;
    private RanchDb ranchDb;

    public PaymentViewModel(Application application) {
        super(application);
        ranchDb = RanchDatabaseRepo.getDb(application);
        paymentDao = ranchDb.getPaymentDao();
    }


    public void insert(Payment payment) {
        new InsertAsyncTask(paymentDao).execute(payment);
    }

    public void delete() {
        new DeleteAsyncTask(paymentDao).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Payment, Void, Void> {

        PaymentDao payDao;

        public InsertAsyncTask(PaymentDao payDao) {
            this.payDao = payDao;
        }

        @Override
        protected Void doInBackground(Payment... payments) {
            payDao.insert(payments[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Payment, Void, Void> {

        PaymentDao payDao;

        public DeleteAsyncTask(PaymentDao payDao) {
            this.payDao = payDao;
        }

        @Override
        protected Void doInBackground(Payment... payments) {
            payDao.deleteAll();
            return null;
        }
    }
}
