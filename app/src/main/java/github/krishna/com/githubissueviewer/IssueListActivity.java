package github.krishna.com.githubissueviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

/**
 * Created by Krishna on 2/4/2016.
 */
public class IssueListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_list);
        ListView issuesList = (ListView) findViewById(R.id.issuesList);
        Intent intent = getIntent();
        ArrayList<String> titleList = intent.getStringArrayListExtra("titleList");
        ArrayList<String> bodyList = intent.getStringArrayListExtra("bodyList");

        issuesList.setAdapter(new MyAdapter(this, titleList, bodyList));


    }
}

class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titlesList;
    private ArrayList<String> bodyList;

    public MyAdapter(Context context, ArrayList<String> titlesList, ArrayList<String> bodyList) {
        this.context = context;
        this.titlesList = titlesList;
        this.bodyList = bodyList;
    }

    @Override
    public int getCount() {
        return titlesList.size();
    }

    @Override
    public Object getItem(int position) {
        return titlesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(titlesList.get(position));
        text1.setTextColor(0xffffffff);
        text1.setBackgroundColor(0xffff0000);
        text2.setText(bodyList.get(position));

        return twoLineListItem;
    }
}