package blocked.licenses;

public class BlockedLicenseIdsAssignLicense extends BlockedLicenseIdsBase{
    private static BlockedLicenseIdsAssignLicense INSTANCE;
    public static synchronized BlockedLicenseIdsAssignLicense getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new BlockedLicenseIdsAssignLicense();
        }
        return INSTANCE;
    }
}
