package com.example.demo.service.list;

import com.example.demo.service.dto.OrderInfo;
import org.springframework.data.domain.Page;

public interface Caller {

    Page<OrderInfo> getList();
}
