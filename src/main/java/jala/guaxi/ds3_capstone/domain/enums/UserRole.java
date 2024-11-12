package jala.guaxi.ds3_capstone.domain.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

     UserRole(String role){
        this.role = role;
    }

    public String getRole() {
         return role;
    }
}
