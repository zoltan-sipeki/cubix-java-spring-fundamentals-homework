package hu.cubix.hr.zoltan_sipeki.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.cubix.hr.zoltan_sipeki.dto.CompanyDto;
import hu.cubix.hr.zoltan_sipeki.model.Company;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public abstract class CompanyMapper {
    public abstract Company mapDtoToCompany(CompanyDto dto);
    
    @Named("WithEmployees")
    public abstract CompanyDto mapCompanyToDto(Company company);

    @Named("WithoutEmployees")
    @Mapping(target = "employees", ignore = true)
    public abstract CompanyDto mapCompanyToDtoWithoutEmployees(Company company);
    
    public abstract List<CompanyDto> mapCompanyListToDtoList(List<Company> list);
    
    public List<CompanyDto> mapCompanyListToDtoListWithoutEmployees(List<Company> list) {
        List<CompanyDto> result = new ArrayList<>();
        for (var company : list) {
            result.add(mapCompanyToDtoWithoutEmployees(company));
        }

        return result;
    }
}
