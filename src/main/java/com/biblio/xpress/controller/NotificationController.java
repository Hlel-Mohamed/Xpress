package com.biblio.xpress.controller;

import com.biblio.xpress.entity.Notification;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.service.NotificationService;
import com.biblio.xpress.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class NotificationController {


    private final NotificationService notificationService;
    private final UserServiceImpl userService;
    @Autowired
    public NotificationController(NotificationService notificationService, UserServiceImpl userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }



    //Get My Notifications
    @GetMapping("/my-notifications/")
    public ResponseEntity<List<Notification>> showMyNotifications(Authentication authentication) {

        Optional<UserEntity> user = userService.findUserByEmail(authentication.getName());
        if (user.isPresent()) {
            List<Notification> notifications = notificationService.getMyNotifications(user.get());
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
