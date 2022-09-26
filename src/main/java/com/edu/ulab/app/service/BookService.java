package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);

//    BookDto updateBook(BookDto bookDto);

    List<BookDto> getBooksByUserId(Long userId);

    void deleteBookByUserId(Long id);
}
