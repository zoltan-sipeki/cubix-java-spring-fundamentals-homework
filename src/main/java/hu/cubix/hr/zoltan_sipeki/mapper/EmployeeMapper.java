package hu.cubix.hr.zoltan_sipeki.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.cubix.hr.zoltan_sipeki.dto.EmployeeDto;
import hu.cubix.hr.zoltan_sipeki.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    
    @Mapping(target = "job", ignore = true)
    public Employee mapDtoToEmployee(EmployeeDto dto);

    @InheritInverseConfiguration
    public EmployeeDto mapEmployeeToDto(Employee model);
    
    public List<EmployeeDto> mapEmployeeListToDtoList(List<Employee> list);

    public List<Employee> mapDtoListToEmployeeList(List<EmployeeDto> list);
}
