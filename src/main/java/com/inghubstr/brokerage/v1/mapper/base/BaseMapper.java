package com.inghubstr.brokerage.v1.mapper.base;

import java.util.List;

public interface BaseMapper<E, DTO> {

    E toEntity(DTO dto);

    List<E> toEntity(List<DTO> dto);

    DTO toDto(E entity);

    List<DTO> toDto(List<E> entity);

}
