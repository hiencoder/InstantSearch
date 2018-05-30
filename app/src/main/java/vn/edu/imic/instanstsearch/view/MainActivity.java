package vn.edu.imic.instanstsearch.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.edu.imic.instanstsearch.R;

public class MainActivity extends AppCompatActivity {
    /*Các kiến thức cơ bản*/
    /*1. Debounce: Toán tử được sử dụng để tập hợp truy vấn tìm kiếm một cách
    * kịp thời thay vì tìm kiếm trên tất cả các ký tự được gõ.
    * 2. Switchmap: Chỉ xem xét các các Observable gần đây và bỏ qua các kết quả trước đó.
    * 3. Ý tưởng:
    * a. Local search: Data contact sẽ được nạp bằng cách gọi đến network. Tìm kiếm sẽ
    * được thực hiện trên 1 danh sách các mảng.
     *b. Remote search:  1 cuộc gọi đến giao thức HTTP sẽ được gọi khi người dùng thực
     * hiện khi người dùng nhập truy vấn tìm kiếm. Việc tìm kiếm được thực hiện
     * trên máy chủ và kết quả sẽ được trả lại.
     * */
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_local_search)
    Button btnLocalSearch;
    @BindView(R.id.btn_remote_search)
    Button btnRemoteSearch;
    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //Tạo background cho notification màu trắng
        whiteNotificationBar(toolbar);
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @OnClick({R.id.btn_local_search,R.id.btn_remote_search})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_local_search:
                openLocalSearch();
                break;
            case R.id.btn_remote_search:
                openRemoteSearch();
                break;
        }
    }

    private void openRemoteSearch() {
        Intent iRemoteSearch = new Intent(this,RemoteSearchActivity.class);
        startActivity(iRemoteSearch);
    }

    private void openLocalSearch() {
        Intent iLocalSearch = new Intent(this,LocalSearchActivity.class);
        startActivity(iLocalSearch);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
