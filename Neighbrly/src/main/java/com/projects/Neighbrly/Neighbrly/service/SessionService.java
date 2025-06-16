package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.entity.Session;
import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT =2;

    public void generateNewSession(User user, String refreshToken){
        List<Session> userSessions = sessionRepository.findByUser(user);
        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }
        Session session =  Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(session);
    }

    public void validateRefreshToken(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(()->new SessionAuthenticationException("session not found for this refresh token"+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(session);

    }
}
