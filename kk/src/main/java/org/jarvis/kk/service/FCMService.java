package org.jarvis.kk.service;

import java.io.FileInputStream;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;

import org.springframework.stereotype.Service;

/**
 * FCMConfig
 */
@Service
public final class FCMService {

    private FirebaseApp firebaseApp;

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("C:/Users/SH/FireBaseDB/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("").build();
            FirebaseApp.initializeApp(options, "options");
        } catch (Exception e) {
            //spring boot 재시작 시 bean이 생성된 상태에서 또다시 호출되는 듯
            //따라서 이미 option이 있음에도 또다시 같은 이름으로 초기화 하려 하니 오류발생!
            // e.printStackTrace();
        }

        this.firebaseApp = FirebaseApp.getInstance("options");
    }

    public void addAllTopics(String token) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(Arrays.asList(token), "All");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void pushAllFcm() {
        Notification notification = Notification.builder().setTitle("Knock Knock").setBody("특가 정보가 도착했습니다!")
                .setImage("chrome-extension://gojmfdiadefnfndenopbbjcioiigfkbi/msgImg.png").build();

        Message message = Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setFcmOptions(
                                WebpushFcmOptions.withLink("chrome-extension://gojmfdiadefnfndenopbbjcioiigfkbi/"))
                        .build())
                .setNotification(notification).setTopic("All").build();
        try {
            FirebaseMessaging.getInstance(firebaseApp).send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void pushOneFcm(){
        Message message = Message.builder().putData("msg", "SUCCESS")
                .setToken("dlDWt2cGiRYbaiHXiNRLfm:APA91bGSfmtwl3r4wucQ_zH5RJ0dgNa2MvRGEt8PB8LqE2VT9IYWVI0P8A_OoKmg06TdgSuMv40px-v2UOM-00WP2CMar_qp7-JDqyw9K7NrOeHMW3iilQL5yQHT3yg-Ply5B0akgo_w").build();
        try {
            FirebaseMessaging.getInstance(firebaseApp).send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}