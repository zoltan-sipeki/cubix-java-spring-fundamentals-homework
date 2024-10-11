package hu.cubix.hr.zoltan_sipeki.exception;

public class EmployeeAlreadyExistsException extends Exception {
    public EmployeeAlreadyExistsException(long employeeId, long companyId) {
        super("Employee with id " + employeeId + " already exists in company with id " + companyId + ".");
    }

    public EmployeeAlreadyExistsException(long employeeId) {
        super("Employee with id " + employeeId + " already exists.");
    }
}
