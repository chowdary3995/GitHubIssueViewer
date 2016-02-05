package github.krishna.com.githubissueviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Krishna on 2/4/2016.
 */

public class ReposListActivity extends Activity {

    private ListView listOfRepos;

    private String getIssuesUrl;

    private ArrayList<String> reposList;

    private String userName;

    private ArrayList<String> titleList;
    private ArrayList<String> bodyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        listOfRepos = (ListView) findViewById(R.id.listOfRepos);
        Intent intent = getIntent();
        reposList = intent.getStringArrayListExtra(MainActivity.KEY_LIS_OF_REPOS);
        userName = intent.getStringExtra("username");

        listOfRepos.setAdapter(new ArrayAdapter<String>(ReposListActivity.this, android.R.layout.simple_list_item_1, reposList));


        listOfRepos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getIssuesUrl = "https://api.github.com/repos/" + userName + "/" + reposList.get(position) + "/issues";

                NetworkUtils.getGitResponse(getIssuesUrl, new NetworkResponseCallBack<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        parseLisOfRepos(response);
                        Intent listOfIssue = new Intent(ReposListActivity.this, IssueListActivity.class);
                        listOfIssue.putStringArrayListExtra("titleList", titleList);
                        listOfIssue.putStringArrayListExtra("bodyList", bodyList);
                        startActivity(listOfIssue);
                    }
                }, ReposListActivity.this);

            }
        });
    }


    public void parseLisOfRepos(JSONArray serviceResponse) {

        titleList = new ArrayList<String>();
        bodyList = new ArrayList<String>();
        for (int i = 0; i < serviceResponse.length(); i++) {
            try {
                JSONObject jsonObject = serviceResponse.getJSONObject(i);

                titleList.add(jsonObject.getString("title"));
                bodyList.add(jsonObject.getString("body"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (titleList.isEmpty()) titleList.add("No issues found.");
        if (bodyList.isEmpty()) bodyList.add("");
    }
}

