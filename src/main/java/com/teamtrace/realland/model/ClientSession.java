package com.teamtrace.realland.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client_session")
@Cacheable
public class ClientSession {
    @Id
    @Column(name = "session_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(400)")
    private String sessionId;
    @Column(name = "channel", updatable = false, nullable = false, columnDefinition = "SMALLINT(2) UNSIGNED")
    private int channel;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", updatable = false, nullable = false, columnDefinition = Client.PK_TYPE)
    private Client client;
    @Column(name = "session_time", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionTime;
    @Column(name = "last_active_time", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActiveTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
}
