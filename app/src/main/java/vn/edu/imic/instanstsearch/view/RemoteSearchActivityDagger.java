package vn.edu.imic.instanstsearch.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import vn.edu.imic.instanstsearch.R;
import vn.edu.imic.instanstsearch.adapter.ContactAdapter;
import vn.edu.imic.instanstsearch.adapter.ContactAdapterDagger;
import vn.edu.imic.instanstsearch.application.MyApplication;
import vn.edu.imic.instanstsearch.di.component.DaggerRemoteSearchComponent;
import vn.edu.imic.instanstsearch.di.component.RemoteSearchComponent;
import vn.edu.imic.instanstsearch.di.module.RemoteSearchActivityContextModule;
import vn.edu.imic.instanstsearch.di.module.RemoteSearchMvpModule;
import vn.edu.imic.instanstsearch.di.qualifier.ActivityContext;
import vn.edu.imic.instanstsearch.di.qualifier.ApplicationContext;
import vn.edu.imic.instanstsearch.interfaces.OnContactListener;
import vn.edu.imic.instanstsearch.mvp.presenter.RemoteSearchPresenterImpl;
import vn.edu.imic.instanstsearch.mvp.view.RemoteSearchView;
import vn.edu.imic.instanstsearch.network.ApiService;
import vn.edu.imic.instanstsearch.network.model.Contact;
import vn.edu.imic.instanstsearch.utils.LogUtils;

public class RemoteSearchActivityDagger extends AppCompatActivity implements OnContactListener,
        RemoteSearchView.MainView {
    private static final String TAG = RemoteSearchActivityDagger.class.getSimpleName();
    private CompositeDisposable disposable = new CompositeDisposable();
    private PublishSubject<String> publishSubject = PublishSubject.create();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_input_search)
    EditText edInputSearch;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    private Unbinder unbinder;

    @Inject
    RemoteSearchPresenterImpl remoteSearchPresenter;

    @Inject
    ContactAdapterDagger contactAdapter;

    @Inject
    @ApplicationContext
    Context context;

    @Inject
    @ActivityContext
    Context mContext;

    RemoteSearchComponent remoteSearchComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_search_dagger);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        whiteNotificationBar(rvResult);

        remoteSearchComponent = DaggerRemoteSearchComponent.builder()
                .appComponent(MyApplication.getMyApplication(this).getAppComponent())
                .remoteSearchActivityContextModule(new RemoteSearchActivityContextModule(this))
                .remoteSearchMvpModule(new RemoteSearchMvpModule(this))
                .build();
        remoteSearchComponent.injectRemoteSearchActivity(this);
        configRecyclerView();
        remoteSearchPresenter.loadData(publishSubject, disposable, edInputSearch);
    }

    @Override
    public void onContactClick(int position) {

    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showData(List<Contact> listContact) {
        LogUtils.d(TAG, "showData: " + listContact.size());
        contactAdapter.setData(listContact);
    }

    @Override
    public void showError(String e) {
        LogUtils.e(TAG,e);
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void updateData() {

    }

    private void configRecyclerView() {
        contactAdapter = new ContactAdapterDagger(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResult.setLayoutManager(layoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvResult.setAdapter(contactAdapter);
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

}
