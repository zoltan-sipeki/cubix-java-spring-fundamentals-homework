package hu.cubix.hr.zoltan_sipeki.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.cubix.hr.zoltan_sipeki.model.Company;
import hu.cubix.hr.zoltan_sipeki.model.CompanyForm;
import hu.cubix.hr.zoltan_sipeki.model.Employee;
import hu.cubix.hr.zoltan_sipeki.model.MinSalary;
import hu.cubix.hr.zoltan_sipeki.model.Position;

public class TestDataGenerator {

    private static final List<String> EMPLOYEE_NAMES = List.of("Dane Kirlin", "Minerva Leuschke", "Jayden Berge-Auer",
            "Demarco Stark", "Kenya Schmitt", "Shaun Trantow", "Freddie Carter", "Keanu Zulauf", "Nelle Hermiston",
            "Rhea Daugherty", "Ethel Wilkinson", "Jermaine Swaniawski", "Gene Ferry", "Terrence Schultz",
            "Murl Ullrich", "Eladio Feest", "Bernice McDermott", "Jennie Jacobi", "Cleta Feeney", "Royal Corkery",
            "Hosea Littel", "Darrel Bradtke", "Mariane Streich", "Clara Olson", "Lonnie Reichert", "Rhiannon Ratke",
            "Kaelyn Herzog", "Caesar Kessler", "Monty Donnelly", "Nigel Howell", "Ward O'Kon", "Ismael Klein",
            "Rodrick Beer", "Hobart Price-Franey", "Cole Greenfelder", "Jameson Blick-Maggio", "Nedra Corkery",
            "Amir Beer", "Celine Vandervort", "Cleta Johnston");

    private static final List<String> COMPANY_NAMES = List.of("Luettgen - Crona",
            "Prosacco - Trantow",
            "Graham - Connelly",
            "Keebler, Armstrong and Pagac",
            "Mueller, Volkman and Bailey",
            "Stroman and Sons",
            "Skiles and Sons",
            "Schmeler, Satterfield and O'Reilly",
            "Labadie and Sons",
            "Kunde, Barrows and Cartwright");

    private static final List<String> JOB_NAMES = List.of("Legacy Identity Agent",
            "Future Markets Associate",
            "Dynamic Applications Designer",
            "Customer Optimization Representative",
            "Forward Directives Consultant",
            "Central Assurance Representative",
            "Product Operations Designer",
            "Product Assurance Supervisor",
            "Human Accountability Facilitator",
            "Senior Factors Specialist");

    private static final List<String> REGISTRATION_NUMBERS = List.of("5c10c569-0227-44f1-87af-34b7efcad69f",
            "b53efb37-8e3d-4c98-be8e-0806ea365ef2",
            "aad718ca-8afb-440b-aa14-468a7f29aaa7",
            "1527f31e-3144-4bb2-a157-8bbaf37471b0",
            "200eeffd-6d6f-412e-b238-e82e8a030121",
            "5242e750-cd49-4fd8-ba1b-c4932d63ba91",
            "910118b0-daed-4cff-945c-bb5fb7e9f771",
            "9c2181dc-b619-46f7-ba82-6caf8a002bed",
            "747df438-d74e-4f9f-a10d-5a273d0b466d",
            "ae767291-8ed3-4993-8320-5cbe26471a97");

    private static final List<String> ADDRESSES = List.of("Chile Tennessee 88824-2533 North Andrew 675 Howe Valley",
            "Guinea Michigan 67580 New Bernie 52987 W Union Street",
            "Bhutan Wyoming 23622-7738 West Nathanael 294 Springfield Close",
            "Azerbaijan Tennessee 96561-0620 St. Clair Shores 936 S Main Street",
            "Sao Tome and Principe South Dakota 46223-2981 Satterfieldboro 958 N 9th Street",
            "Mali Delaware 35153-6436 New Elenatown 55692 Price Manor",
            "Tokelau Nevada 35756-4676 Brendonborough 167 Jairo Harbor",
            "Tunisia Massachusetts 91533 Youngstown 29012 Railroad Avenue",
            "Greenland Louisiana 67090-8352 Magdalenastead 400 Mueller Throughway",
            "Uruguay Colorado 89514 West Lafayette 2406 Brannon Views");

    private static final List<String> COMPANY_FORM_NAMES = List.of("LLC", "Limited PartnerShip", "Corporation");

    private static final int EMPLOYEE_SALARY_MIN = 600000;
    private static final int EMPLOYEE_SALARY_MAX = 3000001;

    private static final int MIN_SALARY_MIN = 300000;
    private static final int MIN_SALARY_MAX = 1500001;

    private Random random = new Random();

    public List<Position> generatePositions() {
        var positions = new ArrayList<Position>();
        var qValues = Position.Qualification.values();
        for (var name : JOB_NAMES) {
            var position = new Position();
            position.setName(name);
            position.setMinQualification(qValues[random.nextInt(qValues.length)]);

            positions.add(position);
        }

        return positions;
    }

    public List<CompanyForm> generateCompanyForms() {
        var forms = new ArrayList<CompanyForm>();
        for (var name : COMPANY_FORM_NAMES) {
            var companyForm = new CompanyForm();
            companyForm.setName(name);

            forms.add(companyForm);
        }

        return forms;
    }

    public List<Employee> generateEmployees(List<Position> positions, List<Company> companies) {
        var employees = new ArrayList<Employee>();
        for (var name : EMPLOYEE_NAMES) {
            var position = positions.get(random.nextInt(positions.size()));
            var company = companies.get(random.nextInt(companies.size()));
            var firstDay = LocalDateTime.of(random.nextInt(1990, 2024), random.nextInt(1, 13), random.nextInt(1, 29), 0,
                    0);

            var employee = new Employee();
            employee.setCompany(company);
            employee.setJob(position);
            employee.setFirstDay(firstDay);
            employee.setName(name);
            employee.setSalary(random.nextInt(EMPLOYEE_SALARY_MIN, EMPLOYEE_SALARY_MAX));

            employees.add(employee);
        }

        return employees;
    }

    public List<MinSalary> generateMinSalaries(List<Position> positions, List<Company> companies) {
        var minSalaries = new ArrayList<MinSalary>();
        for (var company : companies) {
            for (var position : positions) {
                var minSalary = new MinSalary();
                minSalary.setPosition(position);
                minSalary.setCompany(company);
                minSalary.setMinSalary(random.nextInt(MIN_SALARY_MIN, MIN_SALARY_MAX));

                minSalaries.add(minSalary);
            }
        }

        return minSalaries;
    }

    public List<Company> generateCompanies(List<CompanyForm> companyForms) {
        var companies = new ArrayList<Company>();

        for (int i = 0; i < COMPANY_NAMES.size(); ++i) {
            var company = new Company();
            company.setAddress(ADDRESSES.get(i));
            company.setName(COMPANY_NAMES.get(i));
            company.setRegistrationNumber(REGISTRATION_NUMBERS.get(i));

            var form = companyForms.get(random.nextInt(companyForms.size()));
            company.setForm(form);

            companies.add(company);
        }

        return companies;
    }
}
