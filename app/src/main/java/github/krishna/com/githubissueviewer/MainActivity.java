package github.krishna.com.githubissueviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Krishna on 2/4/2016.
 */
public class MainActivity extends Activity {

    private Button findReposButton;
    private String getRepoUrl;
    private EditText input;
    private ProgressDialog progressDialog;
    private ArrayList<String> listOfRepos;

    public static final String KEY_LIS_OF_REPOS = "listOFRepos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.gitUserName);
        findReposButton = (Button) findViewById(R.id.submitButton);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Fetching data from GitHub");

        findReposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send request to volley
                progressDialog.show();
                getRepoUrl = "https://api.github.com/users/" + input.getText().toString() + "/repos";
                NetworkUtils.getGitResponse(getRepoUrl, new NetworkResponseCallBack<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString() + " krish");
                        //go to next screen only if we receive repos. Toast in case of invalid username
                        if (isResponseError(response)) {
                            progressDialog.dismiss();
                            try {
                                Toast toast = Toast.makeText(MainActivity.this.getApplicationContext(), response.getJSONObject(0).get("errMsg").toString(), Toast.LENGTH_LONG);
                                toast.getView().setBackgroundColor(Color.RED);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 10);
                                toast.show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            ArrayList<String> data = parseLisOfRepos(response);

                            System.out.println(data.toString());
                            progressDialog.dismiss();

                            Intent reposIntent = new Intent(MainActivity.this, ReposListActivity.class);
                            reposIntent.putStringArrayListExtra(KEY_LIS_OF_REPOS, data);
                            reposIntent.putExtra("username", input.getText().toString());
                            startActivity(reposIntent);
                        }
                    }
                }, MainActivity.this);

            }
        });

    }

    private boolean isResponseError(JSONArray response) {
        try {
            if (response.length() == 1 && response.getJSONObject(0).has("errMsg")) return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ArrayList<String> parseLisOfRepos(JSONArray serviceResponse) {

        listOfRepos = new ArrayList<String>();

        for (int i = 0; i < serviceResponse.length(); i++) {
            try {
                JSONObject jsonObject = serviceResponse.getJSONObject(i);

                listOfRepos.add(jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listOfRepos;
    }
}