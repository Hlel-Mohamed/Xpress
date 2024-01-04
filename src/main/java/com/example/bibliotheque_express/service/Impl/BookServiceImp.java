package com.example.bibliotheque_express.service.Impl;

import com.example.bibliotheque_express.model.Book;
import com.example.bibliotheque_express.repository.BookRepo;
import com.example.bibliotheque_express.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookRepo bookRepository;

    @Override
    public void saveBook(Book book) {
        if(book.getId() == null){
            bookRepository.save(book);
        }else{
            Book updateBook = bookRepository.findById(book.getId()).get();
            updateBook.setIsbn(book.getIsbn());
            updateBook.setTitle(book.getTitle());
            updateBook.setAuthor(book.getAuthor());
            updateBook.setPublicationDate(book.getPublicationDate());

            bookRepository.save(updateBook);
        }
    }


    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {

        return bookRepository.findById(id).get();
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findByCategory_Id(categoryId);
    }


    public long getTotalBooks() {
        return bookRepository.count();
    }
}
