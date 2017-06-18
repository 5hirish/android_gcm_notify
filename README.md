# Android GCM Notify
Implements Android Google Cloud Messaging with a LAMP Server.

* Google Cloud Messaging - Pus-Sub Notifications
* This is Virtual Notice Board. Subscribe to your batch in college and start reciving notifications for your batch.
* Notifications can be sent using "curl" too. Use script: android_gcm_notify/send_gcm_message.sh
* Saves the notification/alerts for offline access.
* Can use it in your college/school/university/organisation...

Note: Here are the topics currently in this app (Can be modified as per use case):
1) "/topics/FE";
2) "/topics/SE";
3) "/topics/TE";
4) "/topics/BE";

### Need to setup the GCM API:
> https://developers.google.com/cloud-messaging/android/client

Firebase Cloud Messaging (FCM) is the new version of GCM. It inherits the reliable and scalable GCM infrastructure, plus new features! If you are integrating messaging in a new app, start with FCM. GCM users are strongly recommended to upgrade to FCM, in order to benefit from new FCM features today and in the future. This app too soon will be moving on to FCM.
