package hu.cubix.hr.zoltan_sipeki.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.cubix.hr.zoltan_sipeki.dto.EmployeeDto;
import hu.cubix.hr.zoltan_sipeki.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    public Employee mapDtoToEmployee(EmployeeDto dto);
    public EmployeeDto mapEmployeeToDto(Employee model);
    public List<EmployeeDto> mapEmployeeListToDtoList(List<Employee> list);
    public List<Employee> mapDtoListToEmployeeList(List<EmployeeDto> list);
}
