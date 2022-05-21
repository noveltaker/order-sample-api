package com.example.demo.converter;

import com.example.demo.enums.BaseEnum;

import javax.persistence.AttributeConverter;
import java.util.Optional;

public abstract class BaseEnumConverter<E extends BaseEnum<T>, T>
    implements AttributeConverter<E, T> {

  abstract Class<E> getClassEnums();

  private E foundOf(T t) {
    E[] enums = getClassEnums().getEnumConstants();
    for (E e : enums) {
      if (e.getValue().equals(t)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public T convertToDatabaseColumn(E attribute) {
    return Optional.ofNullable(attribute).orElseThrow(NullPointerException::new).getValue();
  }

  @Override
  public E convertToEntityAttribute(T dbData) {
    return foundOf(dbData);
  }
}
