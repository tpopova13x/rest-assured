package helper;

import lombok.Data;
@Data
public class CommonHelper {
    private static CommonHelper INSTANCE;
    PreconditionsChangeTeam preconditionsChangeTeam;
    PreconditionsAssignLicenses preconditionsAssignLicenses;
    CustomerHelper customerHelper;
    Postconditions postconditions;
    LicenseSupplierForAssignLicenseCall licenseSupplierForAssignLicenseCall;
    LicenseSupplierForChangeTeamCall licenseSupplierForChangeTeamCall;
    AssignLicenseCall assignLicenseCall;
    ChangeLicensesTeam changeLicensesTeam;

    private CommonHelper() {
        preconditionsChangeTeam = new PreconditionsChangeTeam();
        customerHelper = new CustomerHelper();
        postconditions = new Postconditions();
        preconditionsAssignLicenses = new PreconditionsAssignLicenses();
        licenseSupplierForAssignLicenseCall = new LicenseSupplierForAssignLicenseCall();
        licenseSupplierForChangeTeamCall = new LicenseSupplierForChangeTeamCall();
        assignLicenseCall = new AssignLicenseCall();
        changeLicensesTeam = new ChangeLicensesTeam();
    }
    public static synchronized CommonHelper getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CommonHelper();
        }
        return INSTANCE;
    }
}
