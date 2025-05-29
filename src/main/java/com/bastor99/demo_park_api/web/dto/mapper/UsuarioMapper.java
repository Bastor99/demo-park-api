package com.bastor99.demo_park_api.web.dto.mapper;

import com.bastor99.demo_park_api.entity.Usuario;
import com.bastor99.demo_park_api.web.dto.UsuarioCreateDto;
import com.bastor99.demo_park_api.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createDto){
        return new ModelMapper().map(createDto, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario) {
        ModelMapper mapper = new ModelMapper();
        UsuarioResponseDto dto = mapper.map(usuario, UsuarioResponseDto.class);
        dto.setRole(usuario.getRole().name().substring("ROLE_".length()));
        return dto;
    }


}
