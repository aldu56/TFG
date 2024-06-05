package es.pmdm.bibliotecadecontenidomultimedia.dto;

import java.util.List;

public class RegisterUserDto {
    private String username;
    private String password;
    private List<Long> rolIds;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String username, String password, List<Long> rolIds) {
        this.username = username;
        this.password = password;
        this.rolIds = rolIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRolIds() {
        return rolIds;
    }

    public void setRolIds(List<Long> rolIds) {
        this.rolIds = rolIds;
    }
}
