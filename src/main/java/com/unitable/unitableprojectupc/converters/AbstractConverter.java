package com.unitable.unitableprojectupc.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<E, RQ, RS> {

    public abstract RS fromEntity(E entity);
    public abstract E fromRequest(RQ dto);

    public List<RS> fromEntity(List<E> entitys){
        return entitys.stream()
                .map(e -> fromEntity(e))
                .collect(Collectors.toList());
    }

    public List<E> fromRequest(List<RQ> dtos){
        return dtos.stream()
                .map(e -> fromRequest(e))
                .collect(Collectors.toList());
    }
}