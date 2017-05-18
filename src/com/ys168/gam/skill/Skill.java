package com.ys168.gam.skill;

import java.util.Set;

public abstract class Skill {

    protected int id;
    protected int type;
    protected String name;
    protected String description;
    protected Set<SkillTrick> tricks;
    protected Set<String> dogeActions;

    public int execute() {
        return -1;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int practice() {
        return -1;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }
}
