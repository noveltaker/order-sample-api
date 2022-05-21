package com.example.demo.converter;

import com.example.demo.enums.ProductState;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductStateConverter extends BaseEnumConverter<ProductState, String> {
  @Override
  Class<ProductState> getClassEnums() {
    return ProductState.class;
  }
}
