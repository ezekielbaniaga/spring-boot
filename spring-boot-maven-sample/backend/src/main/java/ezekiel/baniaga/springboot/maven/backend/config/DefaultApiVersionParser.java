package ezekiel.baniaga.springboot.maven.backend.config;

import org.springframework.stereotype.Component;

@Component
public class DefaultApiVersionParser implements org.springframework.web.accept.ApiVersionParser {

    @Override
    public Comparable parseVersion(String version) {
        if (version.matches("^[vV]")) {
            version = version.substring(1);
        }
        if (version.matches("\\d+") && !version.matches("\\d[.]\\d")) {
            version = version + ".0";
        }

        return version;
    }
}
