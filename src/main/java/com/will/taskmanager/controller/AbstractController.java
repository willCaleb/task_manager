package com.will.taskmanager.controller;

import com.will.taskmanager.exception.CustomException;
import com.will.taskmanager.exception.ErrorResponse;
import com.will.taskmanager.exception.GlobalExceptionHandler;
import com.will.taskmanager.model.dto.AbstractDto;
import com.will.taskmanager.model.entity.AbstractEntity;
import com.will.taskmanager.utils.Utils;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractController<E extends AbstractEntity, DTO extends AbstractDto> {

    @Autowired
    protected GlobalExceptionHandler globalExceptionHandler;

    @ExceptionHandler(value = CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleControllerException(Exception ex) {
        return globalExceptionHandler.handleException(ex);
    }

    Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
    private final Class<E> entityClass = (Class<E>) genericTypes[0];
    private final Class<DTO> dtoClass = (Class<DTO>) genericTypes[1];

    protected Specification<E> getSpecificationEqualOrLike(Map<String, Object> filters) {

        String orderBy = (String) filters.getOrDefault("orderBy", "id");
        String sort = (String) filters.getOrDefault("sort", "desc");

        filters.remove("orderBy");
        filters.remove("sort");

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String chave = entry.getKey();
                Object valor = entry.getValue();

                if (valor != null) {
                    if (String.class.equals(root.get(chave).getJavaType())) {
                        predicates.add(criteriaBuilder.like(root.get(chave), "%" + valor + "%"));
                    } else if (root.get(chave).getJavaType().isEnum()) {
                        Class<Enum> enumType = (Class<Enum>) root.get(chave).getJavaType();
                        Enum enumValue = Enum.valueOf(enumType, valor.toString());
                        predicates.add(criteriaBuilder.equal(root.get(chave), enumValue));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(chave), valor));
                    }
                }
            }

            if ("asc".equals(sort)) {
                query.orderBy(criteriaBuilder.asc(root.get(orderBy)));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(orderBy)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected E toEntity(DTO dto) {
        return new ModelMapper().map(dto, entityClass);
    }

    protected DTO toDto(E entity) {
        return new ModelMapper().map(entity, dtoClass);
    }

    protected List<E> toEntity(List<DTO> dtoList) {
        List<E> entities = new ArrayList<>();

        dtoList.forEach(dto -> entities.add(toEntity(dto)));

        return entities;
    }

    protected List<DTO> toDto(List<E> entityList) {
        List<DTO> dtos = new ArrayList<>();

        entityList.forEach(entity -> dtos.add(toDto(entity)));

        return dtos;
    }

}
