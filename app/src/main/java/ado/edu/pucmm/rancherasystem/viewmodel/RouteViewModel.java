package ado.edu.pucmm.rancherasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ado.edu.pucmm.rancherasystem.dao.RouteDao;
import ado.edu.pucmm.rancherasystem.db.RanchDb;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Route;

public class RouteViewModel extends AndroidViewModel {
    private RouteDao routeDao;
    private RanchDb ranchDb;
    private RanchDatabaseRepo repository;

    public RouteViewModel(Application application) {
        super(application);
        ranchDb = RanchDatabaseRepo.getDb(application);
        routeDao = ranchDb.getRouteDao();
        repository = new RanchDatabaseRepo(application);
        //routes = routeDao.getAllRoutes();
    }


    public void delete() {
        new DeleteAsyncTask(routeDao).execute();
    }

    public void insert(Route route) {
        new InsertAsyncTask(routeDao).execute(route);
    }

    //public LiveData<List<Route>> getAllRoutes(){ return routes; }
    /*
    public List<Route> getAllRoutes() {

        if (routeDao == null) {
            routeDao = ranchDb.getRouteDao();
        }

        List<Route> routes = null;

        try {
            routes = new AllRoutesAsyncTask(routeDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return routes;
    }


    private class AllRoutesAsyncTask extends AsyncTask<Void, Void, List<Route>>{

        RouteDao routeDao;

        public AllRoutesAsyncTask(RouteDao routeDao){this.routeDao = routeDao;}

        @Override
        protected List<Route> doInBackground(Void... voids) {
            return routeDao.getAllRoutes();
        }
    }
    */


    private class InsertAsyncTask extends AsyncTask<Route, Void, Void> {

        RouteDao routeDao;

        public InsertAsyncTask(RouteDao routeDao) {
            this.routeDao = routeDao;
        }

        @Override
        protected Void doInBackground(Route... routes) {
            routeDao.insert(routes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Route, Void, Void> {

        RouteDao routeDao;

        public DeleteAsyncTask(RouteDao routeDao) {
            this.routeDao = routeDao;
        }

        @Override
        protected Void doInBackground(Route... routes) {
            routeDao.deleteAll();
            return null;
        }
    }
}