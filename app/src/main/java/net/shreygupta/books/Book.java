package net.shreygupta.books;


public class Book {

    private String mBookTitle;
    private String mAuthorName;

    public Book(String bookTitle, String authorName) {
        mBookTitle = bookTitle;
        mAuthorName = authorName;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public String getAuthorName() {
        return mAuthorName;
    }
}