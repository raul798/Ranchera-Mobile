package ado.edu.pucmm.rancherasystem.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Client.class, Bill.class, Product.class,
        Support.class, HelpRequest.class, Factura.class, Producto.class, Detalle.class, Pago.class, }, version = 1)
public abstract class RancheraDB extends RoomDatabase {

    public abstract ClientDao clientDao();
    public abstract HelpRequestDao helpRequestDao();

    public abstract SupportDao supportDao();
    public abstract FacturaDao facturaDao();
    public abstract ProductoDao productoDao();
    public abstract DetalleDao detalleDao();

    public abstract PagoDao pagoDao();

    private static volatile RancheraDB INSTANCE;

    static RancheraDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RancheraDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RancheraDB.class, "ranchera_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
}