package jala.guaxi.ds3_capstone.domain.dto;

import jala.guaxi.ds3_capstone.domain.enums.UserRole;

public record RegisterDTO( String login, String password, UserRole role) {
}
