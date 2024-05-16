package com.ftn.sbnz.model.events;

import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;


@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class FailedLoginAttempt {
    public long user;
    public String ip;
    public Date timestamp;

    public FailedLoginAttempt(long user, String ip){
        this.user = user;
        this.ip = ip;
        this.timestamp = new Date();
    }

    public long getUser(){
        return this.user;
    }
    public String getIp(){
        return this.ip;
    }
}
