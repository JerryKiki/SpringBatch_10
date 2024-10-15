DROP DATABASE IF EXISTS SpringBatch_10;
CREATE DATABASE SpringBatch_10;
USE SpringBatch_10;

DROP DATABASE IF EXISTS SpringBatch_10_backup;
CREATE DATABASE SpringBatch_10_backup;
USE SpringBatch_10_backup;

SHOW TABLES;

### Spring 실행 시 추가되는 테이블 : Batch 기본 테이블(기록용), 커스텀 테이블과 섞어쓴다.

# mysqldump -u root SpringBatch_10 | mysql -u root SpringBatch_10_backup << xampp에서 shell 열어서 백업하는 법
# mysqldump -u root SpringBatch_10 > SpringBatch_10.sql << xampp에서 shell 열어서 sql 파일로 만드는 법 (데이터 및 쿼리 저장)
# mysql -u root SpringBatch_10_backup < SpringBatch_10.sql << xampp에서 shell 열어서 백업에 sql 파일을 집어넣는 법