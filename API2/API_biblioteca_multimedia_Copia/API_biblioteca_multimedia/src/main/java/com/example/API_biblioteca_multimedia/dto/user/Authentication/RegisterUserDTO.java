package com.example.API_biblioteca_multimedia.dto.user.Authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO
{
    private String password;
    private String username;
    private List<Long> rolIds;
}
