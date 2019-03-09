package ado.edu.pucmm.rancherasystem;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ado.edu.pucmm.rancherasystem.db.Bill;
import ado.edu.pucmm.rancherasystem.db.BillDao;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;

public class ListOfBills extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BillListAdapter adapter = new BillListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BillDao mDao;

        PopulateDbAsync(RancheraDB db) {
            mDao = db.billDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Bill bill = new Bill(1, 500, "Primer test de esto", 1);
            mDao.insert(bill);
            bill = new Bill(2,5000,"segundo test", 1);
            mDao.insert(bill);
            return null;
        }
    }
}
