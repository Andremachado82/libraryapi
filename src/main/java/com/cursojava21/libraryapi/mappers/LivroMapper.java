package com.cursojava21.libraryapi.mappers;

import com.cursojava21.libraryapi.dto.LivroRequestDTO;
import com.cursojava21.libraryapi.dto.LivroResponseDTO;
import com.cursojava21.libraryapi.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LivroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    Livro toEntity(LivroRequestDTO dto);

    LivroResponseDTO toResponseDto(Livro livro);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    void updateEntityFromDTO(LivroRequestDTO dto, @MappingTarget Livro entity);
}
