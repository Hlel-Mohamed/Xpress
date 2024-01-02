package com.biblio.xpress.repository;

import com.biblio.xpress.entity.Book;
import com.biblio.xpress.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findFirstByBookAndIsAvailableFalse(Optional<Book> book);
    Reservation findFirstByBookAndIsAvailableFalseAndNotificationCount(Optional<Book> book, int notificationCount);


}
