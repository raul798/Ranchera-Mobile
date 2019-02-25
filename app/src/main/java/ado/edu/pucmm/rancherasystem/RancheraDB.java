package ado.edu.pucmm.rancherasystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RancheraDB extends SQLiteOpenHelper {

    private static String dbName = "rancheradb";
    private static String tableName = "client";
    private static String idColumn = "id";
    private static String nameColumn = "name";
    private static String phoneNumberColumn = "phoneNumber";
    private static String emailColumn = "email";
    private static String addressColumn = "address";

    public RancheraDB(Context context) {
        super(context, dbName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabse) {
        sqLiteDatabse.execSQL("create table " + tableName + "(" +
                idColumn + " integer primary key autoincrement, " +
                nameColumn + " text, " +
                phoneNumberColumn + " text, " +
                emailColumn + " text, " +
                addressColumn + " text " +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Client> search(String keyword) {
        List<Client> clients = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName +
                    " where "+ nameColumn + " like ?", new String[] { "%" + keyword + "%"});
            if(cursor.moveToFirst()) {
                clients = new ArrayList<Client>();
                do {
                    Client client = new Client();
                    client.setId(cursor.getInt(0));
                    client.setName(cursor.getString(1));
                    client.setPhoneNumber(cursor.getString(2));
                    client.setEmail(cursor.getString(3));
                    client.setAddress(cursor.getString(4));
                    clients.add(client);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            clients =  null;
        }
        return clients;
    }

    public boolean create(Client client) {
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(nameColumn, client.getName());
            contentValues.put(phoneNumberColumn, client.getPhoneNumber());
            contentValues.put(emailColumn, client.getEmail());
            contentValues.put(addressColumn, client.getAddress());
            return sqLiteDatabase.insert(tableName, null, contentValues) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
