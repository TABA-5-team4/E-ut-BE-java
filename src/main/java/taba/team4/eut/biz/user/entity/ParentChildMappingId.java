package taba.team4.eut.biz.user.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ParentChildMappingId implements Serializable {
    private Long parentId;
    private Long childId;

    public ParentChildMappingId(Long parentId, Long childId) {
        this.parentId = parentId;
        this.childId = childId;
    }


}
