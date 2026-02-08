package co.edu.unbosque.model;

public class Book {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int stock;
    private int availableStock;

    public Book() {
    }

    public Book(String id, String title, String author, String isbn, int stock, int availableStock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stock = stock;
        this.availableStock = availableStock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public boolean isAvailable() {
        return availableStock > 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID='" + id + '\'' +
                ", Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", ISBN='" + isbn + '\'' +
                ", Stock=" + stock +
                ", Available=" + availableStock +
                '}';
    }
}
