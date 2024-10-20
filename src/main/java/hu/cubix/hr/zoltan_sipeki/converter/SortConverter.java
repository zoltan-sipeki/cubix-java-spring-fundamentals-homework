package hu.cubix.hr.zoltan_sipeki.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

// parameter syntax: {property_name}:[asc|desc][,{property_name}:[asc|desc]...] e.g.: salary:asc,name:desc
@Component
public class SortConverter implements Converter<List<String>, Sort> {
    @Override
    public Sort convert(List<String> source) {
        if (source == null) {
            return Sort.unsorted();
        }
        
        var orders = new ArrayList<Order>();
        for (var param : source) {
            var orderStr = param.split(":");
            if (orderStr.length < 2) {
                orders.add(new Order(Sort.DEFAULT_DIRECTION, orderStr[0]));
            }
            else {
                orders.add(new Order(convertStrToDirection(orderStr[1]), orderStr[0]));
            }
        }

        return Sort.by(orders);
    }

    private Direction convertStrToDirection(String dir) {
        switch (dir) {
        case "asc":
            return Direction.ASC;
        case "desc":
            return Direction.DESC;
        }
        return null;
    }
}
