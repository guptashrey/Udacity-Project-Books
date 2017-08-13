package net.shreygupta.books;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

class BookLoader extends AsyncTaskLoader {

    private final String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchBookData(mUrl);
    }
}