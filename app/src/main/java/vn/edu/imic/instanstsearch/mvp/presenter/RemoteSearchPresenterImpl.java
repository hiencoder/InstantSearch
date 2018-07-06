package vn.edu.imic.instanstsearch.mvp.presenter;

import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import vn.edu.imic.instanstsearch.R;
import vn.edu.imic.instanstsearch.mvp.view.RemoteSearchView;
import vn.edu.imic.instanstsearch.network.ApiService;
import vn.edu.imic.instanstsearch.network.model.Contact;
import vn.edu.imic.instanstsearch.utils.Constant;
import vn.edu.imic.instanstsearch.utils.LogUtils;

public class RemoteSearchPresenterImpl implements RemoteSearchView.PresenterRemote {
    private RemoteSearchView.MainView mainView;
    private ApiService apiService;

    @Inject
    public RemoteSearchPresenterImpl(RemoteSearchView.MainView mainView, ApiService apiService) {
        this.mainView = mainView;
        this.apiService = apiService;
    }

    @Override
    public void loadData(final PublishSubject<String> publishSubject, CompositeDisposable disposable, EditText etInput) {
        DisposableObserver<List<Contact>> disposableObserver = new DisposableObserver<List<Contact>>() {
            @Override
            public void onNext(List<Contact> list) {
                LogUtils.d("ListContact", list.size() + "");
                mainView.showData(list);
            }

            @Override
            public void onError(Throwable e) {
                mainView.hideProgress();
                mainView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                mainView.loadSuccess();
                mainView.hideProgress();
            }
        };
        //Fetchdata
/*
        disposable.add(publishSubject.debounce(Constant.TIME_DEBOUNCE, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle(new Function<String, Single<List<Contact>>>() {
                    @Override
                    public Single<List<Contact>> apply(String s) throws Exception {
                        return apiService.getContacts(null, s)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                }).subscribeWith(disposableObserver));
*/

        disposable.add(publishSubject.debounce(Constant.TIME_DEBOUNCE, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle((s) -> {
                    return apiService.getContacts(null,s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                })
                .subscribeWith(disposableObserver));

        //Su kien text change
        disposable.add(RxTextView.textChangeEvents(etInput)
                .skipInitialValue()
                .debounce(Constant.TIME_DEBOUNCE, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                        LogUtils.d("StringInput", textViewTextChangeEvent.text().toString());
                        publishSubject.onNext(textViewTextChangeEvent.text().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.showError(e.getMessage());
                        mainView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        mainView.loadSuccess();
                        mainView.hideProgress();
                    }
                }));

        disposable.add(disposableObserver);

        publishSubject.onNext("");
    }

    @Override
    public void updateData(PublishSubject<String> publishSubject, CompositeDisposable disposable, EditText etInput) {

    }
}
