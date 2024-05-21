package taba.team4.eut.aws.s3;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AwsS3Dto {
    private String key;
    private String path;

    @Builder
    public AwsS3Dto(String key, String path) {
        this.key = key;
        this.path = path;
    }

    public Map<String, Object> toMap() {
        Map map = new HashMap();

        map.put("key", key);
        map.put("path", path);
        return map;
    }
}