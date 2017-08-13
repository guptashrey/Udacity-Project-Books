package net.shreygupta.books;


class Book {

    private final String mBookTitle;
    private final String mAuthorName;

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