package tech.linard.android.mybooks;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity   {
    private EditText searchField;
    private ImageView searchButton;
    private ListView booksListView;
    private List<Volume> asyncVolume;
    final String BASE_URL= "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARM = "q";
    private NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        searchField = (EditText) findViewById(R.id.search_field);
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                performSearch();
                return true;
            }
        });
        searchButton = (ImageView) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            findViewById(R.id.no_connection).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.no_connection).setVisibility(View.GONE);
        }
    }
    private void performSearch() {
        if (networkInfo != null && networkInfo.isConnected()) {
            String parm = searchField.getText().toString();
            if (!TextUtils.isEmpty(parm)) {
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARM, parm)
                        .build();
                URL queryURL = null;
                try {
                    queryURL = new URL(builtUri.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask();
                networkAsyncTask.execute(queryURL);
            }
        }
    }

    private class NetworkAsyncTask extends AsyncTask<URL, Void, List<Volume>> {
        @Override
        protected void onPostExecute(List<Volume> volumes) {
            booksListView = (ListView) findViewById(R.id.list_books);
            booksListView.setAdapter(new VolumeAdapter(MainActivity.this, volumes));
            Toast.makeText(MainActivity.this, "FIM DE TASK", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Volume> doInBackground(URL... urls) {
            asyncVolume = Utils.fetchVolumeData(urls[0]);
            return asyncVolume;
        }
    }
}
