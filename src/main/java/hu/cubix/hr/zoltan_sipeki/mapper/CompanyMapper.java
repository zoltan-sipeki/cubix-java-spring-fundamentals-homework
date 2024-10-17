package hu.cubix.hr.zoltan_sipeki.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.cubix.hr.zoltan_sipeki.dto.CompanyDto;
import hu.cubix.hr.zoltan_sipeki.model.Company;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface CompanyMapper {

    public Company mapDtoToCompany(CompanyDto dto);
    
    @Named("WithEmployees")
    public CompanyDto mapCompanyToDto(Company company);

    @Named("WithoutEmployees")
    @Mapping(target = "employees", ignore = true)
    public CompanyDto mapCompanyToDtoWithoutEmployees(Company company);
    
    public List<CompanyDto> mapCompanyListToDtoList(List<Company> list);
    
    @IterableMapping(qualifiedByName = "WithoutEmployees")
    public List<CompanyDto> mapCompanyListToDtoListWithoutEmployees(List<Company> list);
}
