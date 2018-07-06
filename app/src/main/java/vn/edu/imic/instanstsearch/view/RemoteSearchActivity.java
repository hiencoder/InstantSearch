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
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import vn.edu.imic.instanstsearch.R;
import vn.edu.imic.instanstsearch.adapter.ContactAdapter;
import vn.edu.imic.instanstsearch.interfaces.OnContactListener;
import vn.edu.imic.instanstsearch.network.ApiClient;
import vn.edu.imic.instanstsearch.network.ApiService;
import vn.edu.imic.instanstsearch.network.model.Contact;
import vn.edu.imic.instanstsearch.utils.LogUtils;

public class RemoteSearchActivity extends AppCompatActivity implements OnContactListener {
    private static final String TAG = RemoteSearchActivity.class.getSimpleName();
    private CompositeDisposable disposable = new CompositeDisposable();
    private PublishSubject<String> publishSubject = PublishSubject.create();
    private ApiService apiService;
    private ContactAdapter contactAdapter;
    private List<Contact> listContact = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_input_search)
    EditText edInputSearch;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;
    private Unbinder unbinder;

    /*PublishSubject: Phát ra các sự kiện tại thời điểm đăng ký. Ở đây
     * publishSubject.onNext() sẽ gọi lại phát xạ của Observable 1 lần nữa do đó
      * thực hiện cuộc gọi mạng mới hơn.
      * 2. apiService.getContacts(): Lấy về danh sách contact.
      * 3. switchMapSingle(): Đóng vai trò quan trọng ở đây. Khi có nhiều yêu cầu
      * tìm kiếm trong hàng đợi, switchmap bỏ qua phát xạ trước đó và chỉ xem xét truy
      * vấn hiện tại. Vì vậy danh sách sẽ luôn hiển thị kết quả mới nhất.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_search);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configRecyclerView();

        whiteNotificationBar(rvResult);

        apiService = ApiClient.getClient().create(ApiService.class);
        DisposableObserver<List<Contact>> observer = getSearchObserver();
        /*fetch data*/
        disposable.add(
                publishSubject
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .switchMapSingle(new Function<String, Single<List<Contact>>>() {
                            @Override
                            public Single<List<Contact>> apply(String s) throws Exception {
                                return apiService.getContacts(null, s)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread());
                            }
                        })
                        .subscribeWith(observer));

        //Sự kiện text change
        //skipInitialValue() - skip lần đầu khi edittext empty
        disposable.add(RxTextView.textChangeEvents(edInputSearch)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContacts()));

        disposable.add(observer);

        //Lần đầu truyền vào empty
        publishSubject.onNext("");
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContacts() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                LogUtils.d(TAG, "Search query: " + textViewTextChangeEvent.text());
                publishSubject.onNext(textViewTextChangeEvent.text().toString());
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG, "onComplete");
            }
        };
    }

    private DisposableObserver<List<Contact>> getSearchObserver() {
        return new DisposableObserver<List<Contact>>() {
            @Override
            public void onNext(List<Contact> contacts) {
                if (contacts != null && contacts.size() > 0) {
                    listContact.clear();
                    listContact.addAll(contacts);
                    contactAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG, "onComplete");
            }
        };
    }

    private void configRecyclerView() {
        contactAdapter = new ContactAdapter(this, listContact, this);
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

    @Override
    public void onContactClick(int position) {

    }
}
