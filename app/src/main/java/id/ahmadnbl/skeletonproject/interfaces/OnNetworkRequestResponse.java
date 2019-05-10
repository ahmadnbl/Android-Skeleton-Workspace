package id.ahmadnbl.skeletonproject.interfaces;


import id.ahmadnbl.skeletonproject.data.model.response.GenericResp;

/**
 * Created by billy on 8/8/16.
 *
 */
public interface OnNetworkRequestResponse {
    void onResponseSuccess(int requestTAG, Object response);
    void onResponseFailure(int requestTAG, GenericResp failureResponse);
    void onError(int requestTAG, Throwable e);
}
