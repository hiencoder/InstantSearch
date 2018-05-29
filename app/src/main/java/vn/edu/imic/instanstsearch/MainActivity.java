package vn.edu.imic.instanstsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
     * trên máy chủ và kết quả sẽ được trả lại.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
