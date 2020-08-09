package com.toteuch.rsl.tool.webservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PaginationUtil {
	
	public static Pageable getPageable(Integer page, Integer size, List<String> sort) {
		Pageable pageable = null;
		List<Sort.Order> orders = new ArrayList<>();
        for (String propOrder: sort) {
            String[] propOrderSplit = propOrder.split(",");
            String property = propOrderSplit[0];
            if (propOrderSplit.length == 1) {
                orders.add(new Sort.Order(Direction.ASC,property));
            } else {
                Sort.Direction direction = Sort.Direction.fromOptionalString(propOrderSplit[1]) == null?null:Sort.Direction.fromString(propOrderSplit[1]);
                orders.add(new Sort.Order(direction, property));
            }
        }
		if(sort.isEmpty()) {
			pageable = PageRequest.of(page.intValue(), size.intValue());
		} else {
			pageable = PageRequest.of(page.intValue(), size.intValue(), orders.isEmpty() ? null : Sort.by(orders));
		}
		return pageable;
	}

}
