DROP DATABASE IF EXISTS SpringBatch_10;
CREATE DATABASE SpringBatch_10;
USE SpringBatch_10;

DROP DATABASE IF EXISTS SpringBatch_backup;
CREATE DATABASE SpringBatch_10_backup;
USE SpringBatch_10_backup;

SHOW TABLES;

### Spring 실행 시 추가되는 테이블 : Batch 기본 테이블(기록용), 커스텀 테이블과 섞어쓴다.