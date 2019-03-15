package ado.edu.pucmm.rancherasystem;

import android.app.Application;

import ado.edu.pucmm.rancherasystem.db.Detalle;
import ado.edu.pucmm.rancherasystem.db.DetalleViewModel;
import ado.edu.pucmm.rancherasystem.db.Factura;
import ado.edu.pucmm.rancherasystem.db.FacturaDao;
import ado.edu.pucmm.rancherasystem.db.FacturaViewModel;
import ado.edu.pucmm.rancherasystem.db.Producto;
import ado.edu.pucmm.rancherasystem.db.ProductoViewModel;
import ado.edu.pucmm.rancherasystem.db.RancheraDatabaseRepo;

public class RancheraApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacturaViewModel facturaViewModel = new FacturaViewModel(this);

        facturaViewModel.delete();
        facturaViewModel.insert(new Factura("Factura de prueba"));

        ProductoViewModel productoViewModel = new ProductoViewModel(this);

        productoViewModel.delete();
        productoViewModel.insert(new Producto("aceite", 50));
        productoViewModel.insert(new Producto("goma", 100));
        productoViewModel.insert(new Producto("laptop", 200));

        DetalleViewModel detalleViewModel = new DetalleViewModel(this);

        detalleViewModel.delete();
        detalleViewModel.insert(new Detalle(1, 1, 3 ));
        detalleViewModel.insert(new Detalle(1, 2, 2 ));
        detalleViewModel.insert(new Detalle(1, 2, 4 ));
        detalleViewModel.insert(new Detalle(1, 3, 5 ));

    }
}
