package controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.me.trivia.MainActivity;

public class AppController extends Application {
    public static  final String Tag=AppController.class.getSimpleName();
    private static  AppController mInstance;
    private RequestQueue  mRequestQueue;
    public static synchronized AppController getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public RequestQueue getRequestQueue() {
       if (mRequestQueue==null){
           mRequestQueue= Volley.newRequestQueue(getApplicationContext());

       }
       return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag)? Tag: tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req){
        req.setTag( Tag);
        getRequestQueue().add(req);
    }


    public void CancelPendingRequests(Object tag) {
        if ( mRequestQueue!=null){
            mRequestQueue.cancelAll(tag);
        }
    }
}
