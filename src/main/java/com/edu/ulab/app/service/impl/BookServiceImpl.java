package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookToBookDto(savedBook);
    }

    /*@Override
    public BookDto updateBook(BookDto bookDto) {
        // реализовать недстающие методы
        return null;
    }*/

    @Override
    public List<BookDto> getBooksByUserId(Long userId) {
        // реализовать недстающие методы
        List<BookDto> bookDtoList = bookRepository.findBookByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookToBookDto)
                .toList();
        return bookDtoList;
    }

    @Override
    public void deleteBookByUserId(Long userId) {
        // реализовать недстающие методы
        bookRepository.deleteAllByUserId(userId);
    }
}
