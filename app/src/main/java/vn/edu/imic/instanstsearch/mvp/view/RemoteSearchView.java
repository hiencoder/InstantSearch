package vn.edu.imic.instanstsearch.mvp.view;

import android.widget.EditText;

import java.util.List;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import vn.edu.imic.instanstsearch.network.model.Contact;

public interface RemoteSearchView {
    interface MainView{
        void showProgress();
        void hideProgress();
        void showData(List<Contact> listContact);
        void showError(String e);
        void loadSuccess();
        void updateData();
    }

    interface PresenterRemote{
        void loadData(PublishSubject<String> publishSubject, CompositeDisposable disposable, EditText etInput);

        void updateData(PublishSubject<String> publishSubject, CompositeDisposable disposable, EditText etInput);
    }
}
