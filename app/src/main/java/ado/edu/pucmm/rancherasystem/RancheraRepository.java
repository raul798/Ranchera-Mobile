package ado.edu.pucmm.rancherasystem;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RancheraRepository {
    private ClientDao clientDao;
    private LiveData<List<Client>> allClients;

    RancheraRepository(Application application) {
        RancheraRoomDatabase db = RancheraRoomDatabase.getDatabase(application);
        clientDao = db.clientDao();
        allClients = clientDao.getAllClients();
    }

    LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public void insert (Client client) {
        new insertAsyncTask(clientDao).execute(client);
    }

    private static class insertAsyncTask extends AsyncTask<Client, Void, Void> {

        private ClientDao asyncTaskDao;

        insertAsyncTask(ClientDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Client... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
