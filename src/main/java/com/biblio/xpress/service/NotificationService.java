package com.biblio.xpress.service;

import com.biblio.xpress.entity.*;
import com.biblio.xpress.repository.NotificationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationRepository notificationRepository;
    public List<Notification> getMyNotifications(UserEntity user){
        return notificationRepository.getByReceiverOrderBySendDateDesc(user);
    }
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void notifyUserBookAvailable(Reservation reservation) {
        if (reservation.isAvailable()) {
            Book book = reservation.getBook();
            Notification notification = new Notification();
            String notificationMessage = "The book '" + book.getTitle() + "' is now available.";
            UserEntity user = entityManager.merge(reservation.getUser());
            if (!entityManager.contains(user)) {
                user = entityManager.merge(user);
            }
            notification.setReceiver(user);
            notification.setSendDate(Date.valueOf(LocalDate.now()));
            notification.setMessage(notificationMessage);
            notificationRepository.save(notification);
            System.out.println("Notify "+user.getEmail()+" "+book.getTitle());
            notificationRepository.save(notification);

            messagingTemplate.convertAndSendToUser(user.getUsername(), "/topic/notification", notificationMessage);
        }
    }



    @Transactional
    public void reminderNotification(Borrow borrow) {
        System.out.println("Reminder "+borrow.getUser().getEmail());
        String notificationMessage = "Reminder: The book '" + borrow.getBook().getTitle() + "' is due for return soon.";
        Notification notification = new Notification();
        UserEntity user = borrow.getUser();
        UserEntity attachedUser = entityManager.merge(user);

        notification.setReceiver(attachedUser);
        notification.setSendDate(Date.valueOf(LocalDate.now()));
        notification.setMessage(notificationMessage);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSendToUser(borrow.getUser().getUsername(), "/topic/notification", notificationMessage);

    }
}

