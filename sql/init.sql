-- 유저 --
CREATE TABLE `MEMBER` (
    `MEMBER_ID`	BIGINT	AUTO_INCREMENT PRIMARY KEY,
    `PHONE`	VARCHAR(255)	NOT NULL,
    `MEMBER_TYPE`	VARCHAR(1)	NOT NULL	COMMENT 'parent: P, child: C',
    `EMAIL`	VARCHAR(255)	NULL	COMMENT '부모는 입력 X',
    `CREATED_AT`	DATETIME	NOT NULL	DEFAULT current_timestamp(),
    `UPDATED_AT`	DATETIME	NULL
);

-- 부모-자식 매핑 --
CREATE TABLE `PARENT_CHILD_MAPPING` (
    `PARENT_ID`	BIGINT	NOT NULL,
    `CHILD_ID`	BIGINT	NOT NULL,
    PRIMARY KEY(`PARENT_ID`, `CHILD_ID`),
    FOREIGN KEY (`PARENT_ID`) REFERENCES `MEMBER`(`MEMBER_ID`),
    FOREIGN KEY (`CHILD_ID`) REFERENCES `MEMBER`(`MEMBER_ID`)
);

-- 리프레시 토큰 저장소 --
CREATE TABLE `REFRESH_TOKEN` (
    `id`	BIGINT	AUTO_INCREMENT PRIMARY KEY,
    `USERNAME`	VARCHAR(255)	NOT NULL,
    `REFRESH` VARCHAR(255)	NOT NULL,
    `EXPIRATION` VARCHAR(255)	NOT NULL,
);