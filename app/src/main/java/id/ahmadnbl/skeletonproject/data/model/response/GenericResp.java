package id.ahmadnbl.skeletonproject.data.model.response;

import com.google.gson.annotations.Expose;

/**
 * Created by billy on 8/3/17.
 *
 */

public class GenericResp {

    @Expose
    private String message;
    @Expose
    private int status;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
