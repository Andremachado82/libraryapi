package com.cursojava21.libraryapi.mappers;

import com.cursojava21.libraryapi.dto.AutorRequestDTO;
import com.cursojava21.libraryapi.dto.AutorResponseDTO;
import com.cursojava21.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AutorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livros", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    Autor toEntity(AutorRequestDTO dto);

    AutorResponseDTO toResponseDTO(Autor autor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livros", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    void updateEntityFromDTO(AutorRequestDTO autorRequestDTO, @MappingTarget Autor autorEntity);
}
