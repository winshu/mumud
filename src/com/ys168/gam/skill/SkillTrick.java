package com.ys168.gam.skill;

/**
 * 招数
 * @author Kevin
 * @since 2017年4月26日
 */
public class SkillTrick {

    private int party;
    private int force;
    private int damage;
    private int level;
    private int dodge;
    private String action;

    private DamageType damageType;

    public String getAction() {
        return action;
    }

    public int getDamage() {
        return damage;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public int getDodge() {
        return dodge;
    }

    public int getForce() {
        return force;
    }

    public int getLevel() {
        return level;
    }

    public int getParty() {
        return party;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setParty(int party) {
        this.party = party;
    }

}
