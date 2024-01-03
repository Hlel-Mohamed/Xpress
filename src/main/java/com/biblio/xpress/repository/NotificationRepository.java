package com.biblio.xpress.repository;

import com.biblio.xpress.entity.Notification;
import com.biblio.xpress.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> getByReceiverOrderBySendDateDesc(UserEntity user);
}
