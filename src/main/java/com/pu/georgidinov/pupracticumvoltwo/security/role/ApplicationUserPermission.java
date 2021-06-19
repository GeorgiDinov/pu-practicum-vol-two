package com.pu.georgidinov.pupracticumvoltwo.security.role;

public enum ApplicationUserPermission {

    SHOPPING_LIST_READ("shopping_list:read"),
    SHOPPING_LIST_WRITE("shopping_list:write"),
    ITEM_READ("item:read"),
    ITEM_WRITE("item:write"),
    UNIT_OF_MEASURE_READ("unit_of_measure:read"),
    UNIT_OF_MEASURE_WRITE("unit_of_measure:write");


    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}