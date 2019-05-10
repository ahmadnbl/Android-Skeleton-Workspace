package id.ahmadnbl.skeletonproject.data.api;


import id.ahmadnbl.skeletonproject.data.model.response.GenericResp;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by billy on 8/8/16.
 * Likers API listing configuration
 */
public interface Api {

    @POST("demo")
    Observable<GenericResp> demo(@Body String request);

}