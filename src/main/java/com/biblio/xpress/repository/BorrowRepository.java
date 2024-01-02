package com.biblio.xpress.repository;

import com.biblio.xpress.entity.Borrow;
import com.biblio.xpress.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUser(UserEntity user);

    List<Borrow> findByReturnDateAndIsReturnedFalse(Date oneDayAheadDate);
}
