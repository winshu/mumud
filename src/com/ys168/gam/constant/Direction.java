package com.ys168.gam.constant;

/**
 * 
 * @author Kevin
 * @version 2017年4月12日
 */
public enum Direction {

    //@formatter:off
    EAST("e", "东方"),
    SOUTHEAST("se", "东南"),
    SOUTH("s", "南方"),
    SOUTHWEST("sw", "西南"),
    WEST("w", "西方"),
    NORTHWEST("nw" ,"西北"),
    NORTH("n", "北方"),
    NORTHEAST("ne", "东北");

    //@formatter:on

    public static Direction get(String code) {
        for (Direction dir : Direction.values()) {
            if (dir.name().toLowerCase().equals(code) || dir._SHORT.equals(code)) {
                return dir;
            }
        }
        return null;
    }

    public static Direction get(int ordinal) {
        for (Direction dir : Direction.values()) {
            if (dir.ordinal() == ordinal) {
                return dir;
            }
        }
        return null;
    }

    public final String _SHORT;
    public final String _NAME;

    Direction(String _short, String _name) {
        this._SHORT = _short;
        this._NAME = _name;
    }

    public Direction reverse() {
        int ordinal = (this.ordinal() + Direction.values().length / 2) & 7;
        return Direction.get(ordinal);
    }

}
