package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotifRequest;
import com.example.notificationservice.exception.MessageSendingException;

public interface NotifService {
    void sendMessageToClient(NotifRequest request,String url,String token) throws MessageSendingException;

}
