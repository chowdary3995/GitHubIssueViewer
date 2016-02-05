package github.krishna.com.githubissueviewer;


import org.json.JSONArray;

/**
 * Created by Krishna on 2/4/2016.
 */
public interface NetworkResponseCallBack<JSONArray> {

    public void onResponse(org.json.JSONArray response);

}
