package net.shreygupta.books;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static List<Book> extractFromJson(String bookJSON) {

        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorsJson = volumeInfo.getJSONArray("authors");
                String[] authors = new String[authorsJson.length()];
                int j;
                String author = "";
                for (j = 0; j < authorsJson.length(); j++) {
                    if (j == 0) {
                        authors[j] = authorsJson.getString(j);
                        author = authors[j];
                    } else {
                        authors[j] = authorsJson.getString(j);
                        author = author + ", " + authors[j];
                    }
                }
                Book book = new Book(title, author);
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }
}