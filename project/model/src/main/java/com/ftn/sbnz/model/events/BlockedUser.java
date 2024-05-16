package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("15m")
public class BlockedUser {
    public long user;
    public String ip;
    public boolean notify;

    public BlockedUser(long user, String ip){
        this.user = user;
        this.ip = ip;
        this.notify = false;
    }

    public long getUser(){
        return this.user;
    }

    public String getIp(){
        return this.ip;
    }

    public boolean getNotify(){
        return this.notify;
    }

    public void setNotify(boolean val){
        this.notify = val;
    }
}
