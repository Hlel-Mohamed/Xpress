package com.biblio.xpress.service;

import com.biblio.xpress.entity.Borrow;
import com.biblio.xpress.entity.Reservation;
import com.biblio.xpress.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledTaskService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private  ReservationService reservationService;
    //Schedule checkReservationStatus to see when a user returns the book
    // he borrowed if there is any other users that reserved that book and notify them one by one more like a queue FCFS
    @Scheduled(fixedRate = 30000)
    public void checkReservationStatus() {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.isAvailable()) {
                int notificationCount = reservation.getNotificationCount();
                if (notificationCount < 2) {
                    notificationService.notifyUserBookAvailable(reservation);
                    reservation.setNotificationCount(notificationCount + 1);
                    reservationRepository.save(reservation);
                } else {
                    reservation.setAvailable(false);
                    reservationRepository.save(reservation);
                    Reservation reservation2 = reservationService.availableReservation(Optional.ofNullable(reservation.getBook()),0);
                    if (reservation2!=null) {
                        reservation2.setAvailable(true);
                        reservation2.setNotificationCount(1);
                        reservationService.addReservation(reservation2);
                        notificationService.notifyUserBookAvailable(reservation2);
                    }
                }
            }
        }
    }
    //Schedule to check books overdue before a day of return day
    @Scheduled(fixedRate = 30000)
    public void handleNotificationsBeforeReturn() {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneDayAhead = currentDate.plusDays(1);
        Date oneDayAheadDate = Date.from(oneDayAhead.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Borrow> borrows = borrowService.findByReturnDateAndIsReturnedFalse(oneDayAheadDate);
        for (Borrow borrow : borrows) {
            notificationService.reminderNotification(borrow);
        }
    }
}


