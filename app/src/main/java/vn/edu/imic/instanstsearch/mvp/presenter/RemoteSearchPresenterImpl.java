package vn.edu.imic.instanstsearch.mvp.presenter;

import javax.inject.Inject;

import vn.edu.imic.instanstsearch.mvp.view.RemoteSearchView;
import vn.edu.imic.instanstsearch.network.ApiService;

public class RemoteSearchPresenterImpl implements RemoteSearchView.PresenterRemote{
    private RemoteSearchView.MainView mainView;
    private ApiService apiService;

    @Inject
    public RemoteSearchPresenterImpl(RemoteSearchView.MainView mainView, ApiService apiService) {
        this.mainView = mainView;
        this.apiService = apiService;
    }

    @Override
    public void loadData() {

    }
}
