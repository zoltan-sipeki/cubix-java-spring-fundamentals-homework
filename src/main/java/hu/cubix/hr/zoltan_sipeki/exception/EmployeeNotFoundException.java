package hu.cubix.hr.zoltan_sipeki.exception;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(long employeeId) {
        super("Employee with id " + employeeId + " does not exist.");
    }
}
