package uz.davrbankautoelon.davrbank.mapper;

import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface MapStructMapper<D,M> {
    M fromDto(D dto);
}
