package vn.edu.imic.instanstsearch.mvp.view;

import java.util.List;

import vn.edu.imic.instanstsearch.network.model.Contact;

public interface LocalSearchView {
    /*Interface chứa các phương thức tương tác trong LocalSearchActivity*/
    interface MainView{
        void showProgress();
        void hideProgress();
        void showData(List<Contact> listContact);
        void showError(String e);
        void loadSuccess();
    }

    /*Interface cho presenter*/
    interface PresenterCallBack{
        void loadData();
    }
}
