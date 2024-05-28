package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.NotifRequest;
import com.example.notificationservice.exception.MessageSendingException;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.NotifService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;


@AllArgsConstructor
@Service
public class NotifServiceImpl implements NotifService {

    private NotificationRepository notificationRepository;
    @Override
    public void sendMessageToClient(NotifRequest request,String url,String token) throws MessageSendingException {

        // Constructing the request body
        String requestBody = "{" +
                "\"messaging_product\": \"whatsapp\"," +
                "\"recipient_type\": \"individual\"," +
                "\"to\": \"" + request.getPhone_number() + "\"," +
                "\"type\": \"text\"," +
                "\"text\": {" +
                "\"preview_url\": false," +
                "\"body\": \"" + request.getMssg() + "\"" +
                "}" +
                "}";


        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Authorization",token)
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            // know save this notif
            saveNotification(request);
        }catch (Exception e){
            throw new MessageSendingException("A problem was acqurred when sending message , \n Error : " + e.getMessage());
        }
    }

    public void saveNotification(NotifRequest request){
        Notification notification = new Notification();
        notification.setRcp_phone(request.getPhone_number());
        notification.setMessage(request.getMssg());
        notification.setDate(new Date());

        notificationRepository.save(notification);
    }


}
