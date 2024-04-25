package com.example.API_biblioteca_multimedia.mappers.User;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.User;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class UserListConverter extends AbstractConverter<List<User>, List<Integer>> {

    @Override
    protected List<Integer> convert(List<User> users) {
        return users.stream().map(User::getId).collect(Collectors.toList());
    }
}
