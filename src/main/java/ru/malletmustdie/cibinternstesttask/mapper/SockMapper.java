package ru.malletmustdie.cibinternstesttask.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.malletmustdie.cibinternstesttask.config.SpringMapperConfig;
import ru.malletmustdie.cibinternstesttask.dto.SockDto;
import ru.malletmustdie.cibinternstesttask.model.Sock;

@Mapper(config = SpringMapperConfig.class)
public interface SockMapper {

    @Mapping(target = "color", source = "color")
    @Mapping(target = "cottonPart", source = "cottonPart")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Sock map(SockDto source);

    @Named("map")
    SockDto map(Sock source);

    @IterableMapping(qualifiedByName = "map")
    List<SockDto> map(List<Sock> source);

}
