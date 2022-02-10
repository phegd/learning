package com.tech26.robotfactory.entities;

public enum RobotPartTypes {
    A("HUMANOID", "FACES"),
    B("LCD", "FACES"),
    C("STEAMPUNK", "FACES"),
    D("HANDS", "ARMS"),
    E("GRIPPERS", "ARMS"),
    F("WHEELS", "MOBILITY"),
    G("LEGS", "MOBILITY"),
    H("TRACKS", "MOBILITY"),
    I("BIOPLASTIC", "MATERIALS"),
    J("METALLIC", "MATERIALS");

    private final String type;
    private final String category;

    RobotPartTypes(String type, String category) {
        this.type = type;
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }
}
