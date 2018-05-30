package vn.edu.imic.instanstsearch.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import vn.edu.imic.instanstsearch.R;
import vn.edu.imic.instanstsearch.adapter.ContactAdapterFilter;
import vn.edu.imic.instanstsearch.interfaces.OnContactListener;
import vn.edu.imic.instanstsearch.network.ApiClient;
import vn.edu.imic.instanstsearch.network.ApiService;
import vn.edu.imic.instanstsearch.network.model.Contact;
import vn.edu.imic.instanstsearch.utils.LogUtils;

public class LocalSearchActivity extends AppCompatActivity implements OnContactListener{
    private static final String TAG = LocalSearchActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_input_search)
    EditText edInputSearch;
    @BindView(R.id.rv_result)
    RecyclerView rvContact;

    private Unbinder unbinder;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ContactAdapterFilter contactAdapterFilter;
    private List<Contact> listContact = new ArrayList<>();
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_search);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configRecyclerView();
        setWhiteNotificationBar(rvContact);

        apiService = ApiClient.getClient().create(ApiService.class);

        disposable.add(RxTextView.textChangeEvents(edInputSearch)
        .skipInitialValue()
        .debounce(300, TimeUnit.MILLISECONDS)
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(searchContact()));
        
        /*source: có giá trị là gmail hoặc linkedin
        * Lấy tất cả contact khi chạy app.
        * Chỉ lấy về gmail*/
        fetchContacts("gmail");
    }

    /**
     *
     * @param source
     */
    private void fetchContacts(String source) {
        disposable.add(apiService
        .getContacts(source,null)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<List<Contact>>() {
            @Override
            public void onSuccess(List<Contact> contacts) {
                listContact.clear();
                if (contacts != null && contacts.size() > 0){
                    listContact.addAll(contacts);
                    contactAdapterFilter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG,e.getMessage());
            }
        }));
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContact() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                LogUtils.d(TAG,"Search query: " + textViewTextChangeEvent.text());
                contactAdapterFilter.getFilter().filter(textViewTextChangeEvent.text());
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG,"onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG,"onComplete");
            }
        };
    }

    private void configRecyclerView() {
        contactAdapterFilter = new ContactAdapterFilter(this,listContact,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvContact.setLayoutManager(layoutManager);
        rvContact.setItemAnimator(new DefaultItemAnimator());
        rvContact.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvContact.setAdapter(contactAdapterFilter);
    }

    private void setWhiteNotificationBar(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
        unbinder.unbind();
        super.onDestroy();

    }

    @Override
    public void onContactClick(int position) {

    }
}
