package hu.cubix.hr.zoltan_sipeki.exception;

public class CompanyAlreadyExistsException extends Exception {
    public CompanyAlreadyExistsException(long companyId) {
        super("Company with id " + companyId + " already exists.");
    }
}
