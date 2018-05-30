package vn.edu.imic.instanstsearch.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.imic.instanstsearch.network.model.Contact;

/**
 * Created by MyPC on 30/05/2018.
 */

public interface ApiService {
    @GET("contacts.php")
    Single<List<Contact>> getContacts(@Query("source") String source,
                                      @Query("search") String search);
}
