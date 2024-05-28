package taba.team4.eut.biz.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARENT_CHILD_MAPPING")
@Getter
@NoArgsConstructor
public class ParentChildMappingEntity {
    @EmbeddedId
    private ParentChildMappingId parentChildMappingId;

    @MapsId("parentId")
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private UserEntity parent;

    @MapsId("childId")
    @ManyToOne
    @JoinColumn(name = "CHILD_ID")
    private UserEntity child;

    public ParentChildMappingEntity(UserEntity parent, UserEntity child) {
        this.parentChildMappingId = new ParentChildMappingId(parent.getMemberId(), child.getMemberId());
        this.parent = parent;
        this.child = child;
    }


}
