package hu.cubix.hr.zoltan_sipeki.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort.Direction;

public class SortOrderConverter implements Converter<String, Direction> {
    @Override
    public Direction convert(String source) {
        source = source.toLowerCase();
        switch (source) {
        case "asc":
            return Direction.ASC;
        case "desc":
            return Direction.DESC;
        }
        return null;
    }
    
}
