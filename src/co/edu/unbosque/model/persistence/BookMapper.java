package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Book;
import co.edu.unbosque.model.dto.BookDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setAvailable(book.isAvailable());
        
        return dto;
    }

    public static Book toEntity(BookDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setAvailable(dto.isAvailable());
        
        return book;
    }

    public static List<BookDTO> toDTOList(List<Book> books) {
        if (books == null) {
            return null;
        }
        
        return books.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Book> toEntityList(List<BookDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(BookMapper::toEntity)
                .collect(Collectors.toList());
    }
}
