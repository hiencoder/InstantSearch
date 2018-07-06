package vn.edu.imic.instanstsearch.mvp.presenter;

import javax.inject.Inject;

import vn.edu.imic.instanstsearch.mvp.view.LocalSearchView;
import vn.edu.imic.instanstsearch.network.ApiService;

public class LocalSearchPresenterImpl implements LocalSearchView.PresenterCallBack{
    //Khai bao instance LocalSearchView.MainView, ApiService
    LocalSearchView.MainView mainView;
    ApiService apiService;

    @Inject
    public LocalSearchPresenterImpl(LocalSearchView.MainView mainView, ApiService apiService) {
        this.mainView = mainView;
        this.apiService = apiService;
    }

    @Override
    public void loadData() {

    }
}
