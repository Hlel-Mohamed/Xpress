function getNotifications() {
    document.addEventListener('DOMContentLoaded', () => {
        const notificationButton = document.getElementById('notification-button');
        const notificationContent = document.getElementById('notification-content');
        const notificationList = document.getElementById('notification-list');

        // Replace this with your API URL
        const apiUrl = '/my-notifications/';
        const token = localStorage.getItem('jwtToken');

        notificationButton.addEventListener('click', () => {
            fetch(apiUrl, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to fetch notifications');
                    }
                    return response.json();
                })
                .then(notifications => {
                    notificationList.innerHTML = ''; // Clear previous notifications
                    notifications.forEach(notification => {
                        const listItem = document.createElement('div');
                        listItem.classList.add('notification-item');

                        const message = document.createElement('p');
                        message.textContent = notification.message;
                        const sendDate = document.createElement('small');
                        sendDate.textContent = notification.sendDate;

                        listItem.appendChild(message);
                        listItem.appendChild(sendDate)
                        notificationList.appendChild(listItem);
                    });

                    notificationContent.style.display = (notificationContent.style.display === 'block') ? 'none' : 'block';
                })
                .catch(error => {
                    console.error('Error fetching notifications:', error);
                    // Handle error - show a message or take appropriate action
                });
        });

        // Close notification window when clicking outside
        window.addEventListener('click', (e) => {
            if (!notificationButton.contains(e.target) && !notificationContent.contains(e.target)) {
                notificationContent.style.display = 'none';
            }
        });
    });
}


