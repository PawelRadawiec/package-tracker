package com.info.packagetrackerbackend.model;

public enum OrganizationColor {

    BLUE("BLUE"), GREEN(("GREEN")), ORANGE("ORANGE"), YELLOW("YELLOW");

    private String color;

    OrganizationColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
