package taba.team4.eut.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter
public abstract class BaseModel {
//    protected String link;

//    public void addLink(String link) {
//        this.link = link;
//    }

    public abstract <E extends BaseEntity> E toEntity();



}
