package taba.team4.eut.common.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


/**
 * ### lombok 클래스 어노테이션
 * @NoArgsConstructor
 *  - 기본 생성자를 만든다
 * @AllArgsConstructor
 *  - 모든 변수에 해당하는 생성자를 만든다
 * @RequiredArgsConstructor
 *  - final 또는 @NonNull 변수 값을 파라미터로 받는 생성자를 만든다 *
 *
 * ### JPA 클래스 어노테이션
 * @Table
 *  - 테이블명, 인덱스 등에 관한 설정 *
 * @DynamicInsert
 *  - insert 되기 전에 엔티티에 설정된 컬럼 정보 중 null이 아닌 컬럼만을 이용하여 동적 insert 쿼리를 생성
 *  - table schema에 default 값이 설정된 컬럼의 경우, 엔티티에 값을 설정하지 않을시 default값으로 저장됨
 *  - (@DynamicInsert 설정이 되어있지 않을 경우라면 null이 들어감.)
 * @DynamicUpdate
 *  - 엔티티 update 할 때, 변경된 컬럼정보만을 이용하여 동적 쿼리를 생성
 *
 * ## JPA 변수 어노테이션
 * @Id
 *  - PK 키임을 명시
 *  - Id 생성 규칙을 IDENTITY로 설정하면 키생성을 database에게 위임한다.
 *  - 엔티티 저장시 id를 별도로 설정하지 않으면 MySQL의 AUTO_INCREMENT를 이용하여 자동으로 PK가 생성된다.
 * @Column
 *  - spring.jpa.generate-ddl 를 true로 설정할 경우 @Column에 정의한 행태로 테이블이 생성된다.
 *      => 실제 운영에서는 이렇게 설정할 경우가 없음.
 *  - nullable, length, unique 등을 설정할 수 있고 DEFAULT 값 설정과 같은 상세 설정은 columnDefinition으로 설정할 수 있다.
 *  - length는 String 값에만 설정할 수 있는 기능이다.
 * @ColumnDefault
 *  - 컬럼의 기본값
 * @PrePersist
 *  - entity의 값이 persist 영역으로 넘어가기 전에(=DB에 저장되기 전에) 행해져야할 기능을 정의한다.
 * @PreUpdate
 *  - entity값이 update 되기 전에 행해져야할 기능을 정의한다.
 *
 */

@Slf4j
@Getter @Setter
@MappedSuperclass // 단순히 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
public abstract class BaseEntity  {

    public abstract <M extends BaseModel> M toModel();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_AT", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_AT", columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    protected LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
