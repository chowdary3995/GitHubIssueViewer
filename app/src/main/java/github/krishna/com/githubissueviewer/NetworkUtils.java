package github.krishna.com.githubissueviewer;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Krishna on 2/4/2016.
 */
public class NetworkUtils {

    public static void getGitResponse(String url, final NetworkResponseCallBack<JSONArray> callback, Context context) {


        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString() + " krish");
                callback.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                try {
                    String str = error.getMessage();
                    JSONArray errJSONARR = new JSONArray();
                    JSONObject errObj = new JSONObject();
                    errObj.put("errMsg", "Username not found");
                    errJSONARR.put(errObj);
                    callback.onResponse(errJSONARR);
                } catch (JSONException e) {
                   /* e.printStackTrace(); */
                }
                //show alert dailog in case of failure using context object
            }
        });
        queue.add(jsObjRequest);

    }


}
