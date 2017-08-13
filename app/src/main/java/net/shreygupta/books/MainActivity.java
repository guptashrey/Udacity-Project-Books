package net.shreygupta.books;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;
    private EditText search_et;
    private BookAdapter mAdapter;
    private TextView textNoDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_et = (EditText) findViewById(R.id.search_et);
        ImageButton search_button = (ImageButton) findViewById(R.id.search_button);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        final View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setAdapter(mAdapter);

        textNoDataFound = (TextView) findViewById(R.id.text_no_data_found);
        bookListView.setEmptyView(textNoDataFound);

        final LoaderManager loaderManager = getSupportLoaderManager();

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    textNoDataFound.setVisibility(View.INVISIBLE);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {
                    mAdapter.clear();
                    textNoDataFound.setText(getResources().getString(R.string.no_internet_connection));
                    textNoDataFound.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private String createStringURL() {
        String url_basic = getResources().getString(R.string.basic_url);

        if (search_et.getText().toString().equals("")) {
            return null;
        } else
            return url_basic + search_et.getText().toString().replace(" ", "+");
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, createStringURL());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        textNoDataFound.setText(R.string.no_books);

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}