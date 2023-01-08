package app;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiTeamViewer extends ApiBaseClass{
    private static ApiTeamViewer INSTANCE;
    public ApiTeamViewer(){
        super("token.team.viewer");
    }
    public static synchronized ApiTeamViewer getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ApiTeamViewer();
        }
        return INSTANCE;
    }
}
