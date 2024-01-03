package com.biblio.xpress.service;

import com.biblio.xpress.entity.Book;
import com.biblio.xpress.entity.Reservation;
import com.biblio.xpress.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public Reservation addReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Reservation availableReservation(Optional<Book> book,int notificationCount) {
        return reservationRepository.findFirstByBookAndIsAvailableFalseAndNotificationCount(book,notificationCount);
    }


}
