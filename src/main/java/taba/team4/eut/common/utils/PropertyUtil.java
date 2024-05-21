package taba.team4.eut.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class PropertyUtil {

    private final String PROFILE_LOCAL  = "local";
    private final String PROFILE_DEV    = "dev";
    private final String PROFILE_STAGE  = "stage";
    private final String PROFILE_PROD   = "prod";

    private final String[] activeProfile;
    private final int serverNodeNum;

    private final ConfigurableEnvironment env;

    public PropertyUtil(ConfigurableEnvironment env) {
        this.env = env;
        this.activeProfile = env.getActiveProfiles();
        this.serverNodeNum = env.getProperty("nodeNum", Integer.class, 1);

        log.info("[ PROFILES ] ==================================================== ");
        log.info("# activeProfile = {}", activeProfile);
        log.info("# serverNodeNum = {}", serverNodeNum);
        log.info("[ PROFILES ] ==================================================== ");
    }

    public Map<String, Object> getAllProperties() {
        Map<String, Object> allProperties = new TreeMap<>();

        env.getPropertySources().forEach(propertySource -> {
            String propertyName = propertySource.getName();
            if ( propertyName.indexOf("nampEcoCommon") < 1
                    && propertyName.indexOf("application.yml") < 1 ) {
                return;
            }
            Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
            allProperties.putAll(source);
        });

        return allProperties;
    }

    /* ======================================================================================= //
        # Profile
            - jar 실행 명령 설정 : --spring.profiles.active=prod --nodeNum=1
    // ======================================================================================= */
    public String getActiveProfile() {
        return Arrays.stream(this.activeProfile).findFirst().orElseGet(() -> "");
    }
    public boolean isLocal() { return this.hasProfile(PROFILE_LOCAL); }
    public boolean isNotLocal() { return !isLocal(); }
    public boolean isDev() {
        return this.hasProfile(PROFILE_DEV);
    }
    public boolean isStage() {
        return this.hasProfile(PROFILE_STAGE);
    }
    public boolean isProd() {
        return this.hasProfile(PROFILE_PROD);
    }
    private boolean hasProfile(String profile) {
        return Arrays.stream(this.activeProfile).anyMatch(s -> s.equals(profile));
    }

    /* ======================================================================================= //
        # Server node 정보
            - Program argument 설정 : --nodeNum=1
            - Environment valiables 설정 : nodeNum=1
            - jar 실행 명령 설정 : --nodeNum=1
    // ======================================================================================= */
    public int getServerNodeNum(){
        return this.serverNodeNum;
    }
    public boolean isFirstNode() {
        return (serverNodeNum == 1);
    }
    public boolean isNotFirstNode() {
        return !isFirstNode();
    }

}
