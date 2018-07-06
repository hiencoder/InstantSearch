package vn.edu.imic.instanstsearch.mvp.view;

import java.util.List;

import vn.edu.imic.instanstsearch.network.model.Contact;

public interface RemoteSearchView {
    interface MainView{
        void showProgress();
        void hideProgress();
        void showData(List<Contact> listContact);
        void showError(String e);
        void loadSuccess();
    }

    interface PresenterRemote{
        void loadData();
    }
}
