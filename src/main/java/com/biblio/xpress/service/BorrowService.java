package com.biblio.xpress.service;

import com.biblio.xpress.entity.Borrow;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowService {
    private final BorrowRepository borrowRepository;
    @Autowired
    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }
    public Borrow newBorrow(Borrow borrow){
        return borrowRepository.save(borrow);
    }
    public List<Borrow> getMyBorrowedBooks(UserEntity user){
        return borrowRepository.findByUser(user);
    }

    public Borrow getBorrowedBookById(Long borrowId) {
        return borrowRepository.getById(borrowId);
    }

    public void deleteBorrowBook(Long borrowId) {
        borrowRepository.deleteById(borrowId);
    }

    public List<Borrow> findByReturnDateAndIsReturnedFalse(Date oneDayAheadDate) {
        return borrowRepository.findByReturnDateAndIsReturnedFalse(oneDayAheadDate);
    }
}
