-- 유저 --
CREATE TABLE `MEMBER` (
                          `MEMBER_ID`	BIGINT	AUTO_INCREMENT PRIMARY KEY,
                          `PHONE`	VARCHAR(255)	NOT NULL,
                          `PASSWORD`	VARCHAR(255)	NOT NULL,
                          `ROLE`	VARCHAR(255)	NOT NULL,
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
                                 `EXPIRATION` VARCHAR(255)	NOT NULL
);

-- 채팅 --
CREATE TABLE `CHAT` (
                        `CHAT_ID`	BIGINT	AUTO_INCREMENT PRIMARY KEY,
                        `MEMBER_ID`	BIGINT	NOT NULL,
                        `INPUT`	TEXT	NOT NULL,
                        `RESPONSE`	TEXT	NOT NULL,
                        `CREATED_AT`	DATETIME	NOT NULL	DEFAULT current_timestamp(),
                        `UPDATED_AT`	DATETIME	NULL,
                        FOREIGN KEY (`MEMBER_ID`) REFERENCES `MEMBER`(`MEMBER_ID`)
);

-- 채팅 음성 파일 --
CREATE TABLE `CHAT_VOICE` (
                              `CHAT_ID`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              `FILE_NAME`	VARCHAR(255)	NOT NULL,
                              `FILE_URL`	VARCHAR(255)	NOT NULL,
                              `FILE_LENGTH`	BIGINT	NOT NULL,
                              `CREATED_AT`	DATETIME	NOT NULL	DEFAULT current_timestamp(),
                              `UPDATED_AT`	DATETIME	NULL,
                              FOREIGN KEY (`CHAT_ID`) REFERENCES `CHAT`(`CHAT_ID`)
);

-- 유저 통계 --
CREATE TABLE `USER_STATISTICS` (
                                   `STAT_ID`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                   `MEMBER_ID`	BIGINT	NOT NULL,
                                   `STAT_DATE`	DATETIME	NOT NULL,
                                   `SUMMARY`	TEXT	NULL,
                                   `HAPPINESS_SCORE`	DECIMAL(5, 2)	NULL,
                                   `PANIC_SCORE`	DECIMAL(5, 2)	NULL,
                                   `NEUTRAL_SCORE`	DECIMAL(5, 2)	NULL,
                                   `ANXIETY_SCORE`	DECIMAL(5, 2)	NULL,
                                   `ANGER_SCORE`	DECIMAL(5, 2)	NULL,
                                   `SADNESS_SCORE`	DECIMAL(5, 2)	NULL,
                                   `DISGUST_SCORE`	DECIMAL(5, 2)	NULL,
                                   `USAGE_TIME_SECOND`	BIGINT	NULL,
                                   `NEGATIVE_EXP_RATE`	DECIMAL(5, 2)	NULL,
                                   `CREATED_AT`	DATETIME	NOT NULL	DEFAULT current_timestamp(),
                                   `UPDATED_AT`	DATETIME	NULL,
                                   FOREIGN KEY (`MEMBER_ID`) REFERENCES `MEMBER`(`MEMBER_ID`)
);