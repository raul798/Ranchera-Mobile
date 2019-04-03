package ado.edu.pucmm.rancherasystem;

import android.app.Application;

import ado.edu.pucmm.rancherasystem.entity.Faq;
import ado.edu.pucmm.rancherasystem.viewmodel.FaqViewModel;

public class RanchApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FaqViewModel faqViewModel = new FaqViewModel(this);
        faqViewModel.delete();
        faqViewModel.insert(new Faq("que es ranchera?", "es una aplicacion asdfasdfa afdskjfalskdjfal adfja;lsjdkfa;ljdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        faqViewModel.insert(new Faq("que es ranch?", "es una aplicacion"));
        faqViewModel.insert(new Faq("que es perfecto?", "regular"));
        faqViewModel.insert(new Faq("que es ranchera?", "es una aplicacion"));
        faqViewModel.insert(new Faq("que es ranch?", "es una aplicacion"));
        faqViewModel.insert(new Faq("que es perfecto?", "regular"));
        faqViewModel.insert(new Faq("que es ranchera?", "es una aplicacion"));
        faqViewModel.insert(new Faq("que es ranch?", "es una aplicacion"));
        faqViewModel.insert(new Faq("que es perfecto?", "regular"));

    }
}
