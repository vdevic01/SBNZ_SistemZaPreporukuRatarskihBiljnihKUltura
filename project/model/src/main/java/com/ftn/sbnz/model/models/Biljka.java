package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class Biljka {
    @Position(0)
    public String name;

    @Position(1)
    public String parent;

    public Biljka(String name, String parent){
        this.name = name;
        this.parent = parent;
    }

    public String getName(){
        return this.name;
    }

    public String getParent(){
        return this.parent;
    }
}
