package hu.cubix.hr.zoltan_sipeki.exception;

public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException(long companyId) {
        super("Company with id " + companyId + " does not exist.");
    }

}
