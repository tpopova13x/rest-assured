package blocked.licenses;


public class BlockedLicenseIdsChangeTeam extends BlockedLicenseIdsBase{
    private static BlockedLicenseIdsChangeTeam INSTANCE;
        public static synchronized BlockedLicenseIdsChangeTeam getInstance() {
            if(INSTANCE == null) {
                INSTANCE = new BlockedLicenseIdsChangeTeam();
            }
            return INSTANCE;
        }
}
