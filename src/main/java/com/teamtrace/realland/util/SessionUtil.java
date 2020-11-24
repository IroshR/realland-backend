package com.teamtrace.realland.util;

import com.teamtrace.realland.model.*;
import com.teamtrace.realland.repository.AdminUserSessionRepository;
import com.teamtrace.realland.repository.ClientSessionRepository;
import com.teamtrace.realland.repository.HistoryAdminUserSessionRepository;
import com.teamtrace.realland.util.constant.Statuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class SessionUtil {
    @Autowired
    private AdminUserSessionRepository adminUserSessionRepository;
    @Autowired
    private HistoryAdminUserSessionRepository historyAdminUserSessionRepository;
    @Autowired
    private ClientSessionRepository clientSessionRepository;

    public synchronized void addAdminUserSession(String sessionId, AdminUser adminUser) {
        List<AdminUserSession> userSessions =
                adminUserSessionRepository.findAdminUserSessionsByAdminUserId(adminUser.getAdminUserId());

        if (userSessions != null && !userSessions.isEmpty()) {
            for (AdminUserSession userSession : userSessions) {
                HistoryAdminUserSession historyAdminUserSession = new HistoryAdminUserSession();
                historyAdminUserSession.setSessionId(sessionId);
                historyAdminUserSession.setMerchantId(userSession.getMerchantId());
                historyAdminUserSession.setAdminUser(userSession.getAdminUser());
                historyAdminUserSession.setSessionTime(userSession.getSessionTime());
                historyAdminUserSession.setLogoutTime(new Date());

                historyAdminUserSessionRepository.save(historyAdminUserSession);

                adminUserSessionRepository.delete(userSession);
            }
        }


        AdminUserSession userSession = new AdminUserSession();
        userSession.setSessionId(sessionId);
        userSession.setAdminUser(adminUser);
        userSession.setMerchantId(adminUser.getMerchantId());
        userSession.setLastActiveTime(new Date());
        userSession.setSessionTime(new Date());

        adminUserSessionRepository.save(userSession);
    }

    public synchronized AdminUser getAdminUserSession(String sessionId) {
        Optional<AdminUserSession> optional = adminUserSessionRepository.findById(sessionId);

        if (!optional.isPresent()) {
            return null;
        }
        AdminUserSession userSession = optional.get();
        AdminUser adminUser = userSession.getAdminUser();

        userSession.setLastActiveTime(new Date());
        adminUserSessionRepository.save(userSession);

        return adminUser;
    }

    public synchronized boolean removeAdminUserSession(String sessionId) {
        Optional<AdminUserSession> optional = adminUserSessionRepository.findById(sessionId);

        if (!optional.isPresent()) {
            return false;
        }
        AdminUserSession userSession = optional.get();

        HistoryAdminUserSession historyAdminUserSession = new HistoryAdminUserSession();
        historyAdminUserSession.setSessionId(sessionId);
        historyAdminUserSession.setMerchantId(userSession.getMerchantId());
        historyAdminUserSession.setAdminUser(userSession.getAdminUser());
        historyAdminUserSession.setSessionTime(userSession.getSessionTime());
        historyAdminUserSession.setLogoutTime(new Date());

        historyAdminUserSessionRepository.save(historyAdminUserSession);

        adminUserSessionRepository.delete(userSession);
        return true;
    }

    public boolean isValidAdminUserSession(String sessionId) {
        Optional<AdminUserSession> optional = adminUserSessionRepository.findById(sessionId);

        if (!optional.isPresent()) {
            return false;
        }
        AdminUserSession adminUserSession = optional.get();
        AdminUser adminUser = adminUserSession.getAdminUser();
        if (adminUser.getStatus() == Statuses.APPROVED) {
            return true;
        }
        return false;
    }


    public synchronized void addClientSession(String sessionId, Client client) {
        List<ClientSession> clientSessions =
                clientSessionRepository.findClientSessionsByAdminUserId(client.getClientId());

        if (clientSessions != null && !clientSessions.isEmpty()) {
            for (ClientSession clientSession : clientSessions) {
                clientSessionRepository.delete(clientSession);
            }
        }


        ClientSession clientSession = new ClientSession();
        clientSession.setSessionId(sessionId);
        clientSession.setClient(client);
        clientSession.setChannel(client.getChannelId());
        clientSession.setLastActiveTime(new Date());
        clientSession.setSessionTime(new Date());

        clientSessionRepository.save(clientSession);
    }

    public synchronized Client getClientSession(String sessionId) {
        Optional<ClientSession> optional = clientSessionRepository.findById(sessionId);

        if (!optional.isPresent()) {
            return null;
        }
        ClientSession clientSession = optional.get();
        Client client = clientSession.getClient();

        clientSession.setLastActiveTime(new Date());
        clientSessionRepository.save(clientSession);

        return client;
    }
}
