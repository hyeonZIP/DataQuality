/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트용 DB 접근을 담당하는 모듈 입니다.
 */
 const TestDAO = (function () {

    //PostgreSQL DB Connection 객체 - private object(변수)
    const PostgresqlConnection = require('../../../modules/db/postgresql/PostgresqlConnection');
    //console.log('PostgresqlConnection : ', PostgresqlConnection);
    const connectionPool = PostgresqlConnection.getConnectionPool();

    //public object
    return {

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 목록 조회
         */
        testSelectList: function (param) {
            return connectionPool.query('SELECT * FROM test_schema.test_table');
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 상세 조회
         */
        testSelectOne: function (param) {
            return connectionPool.query('SELECT * FROM test_schema.test_table WHERE test_seq = $1', param);
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 등록
         */
        testInsertOne: function (param) {
            return connectionPool.query(
                //RETURNING <*||COLUMN NAME1,COLUMN NAME2,...> : 등록한 Data return 받는 기능
                //ex1) RETURNING * : 모든 컬럼 다 return 함
                //ex2) RETURNING name : 컬럼명이 'name'인 데이터만 return 함
                `INSERT INTO test_schema.test_table (
                    test_seq
                    , test_title
                    , test_content
                    , test_create_datetime
                    , test_create_user
                ) VALUES (
                    (SELECT COALESCE(MAX(test_seq), 0) + 1 FROM test_schema.test_table)
                    , $1
                    , $2
                    , current_timestamp
                    , 1
                ) RETURNING test_seq`,
                param
            );
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 수정
         */
        testUpdateOne: function (param) {
            return connectionPool.query(
                `UPDATE
                    test_schema.test_table
                SET 
                    test_title = $2
                    , test_content = $3
                    , test_update_datetime = current_timestamp
                    , test_update_user = 1
                WHERE
                    test_seq = $1`,
                param
            );
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 삭제
         */
        testDeleteOne: function (param) {
            return connectionPool.query(
                `DELETE
                FROM
                    test_schema.test_table
                WHERE
                    test_seq = $1`,
                param
            );
        },


        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 트랜젝션 처리
         * 
         * 트랜잭션 작업 진행 시나리오
         *  1. connection pool 한 개 받아오기
         *  2. BEGIN(트랜잭션 시작 처리)
         *  3. [※작업내용1] 데이터 등록
         *  4. [※작업내용2] 등록한 데이터 수정
         *  5. ＊작업 완료시, 실행(dispatch)한 모든 쿼리를 COMMIT(커밋)
         *     ＊에러 발생시, 실행(dispatch)한 모든 쿼리를 ROLLBACK(롤백)
         *  6. 받아온 connection pool 반납(해제) - 필수(반납하지 않으면 해당 pool을 사용하는 query는 트랜잭션에 계속 매달려있음)
         */
         testTransaction: function (param) {
            return new Promise((resolve, reject) => {
                //1. connection pool 한 개 받아오기
                const poolClient = PostgresqlConnection.getConnectionPoolClient();
                poolClient.then(async(client) => {//async 처리
                    try {
                        //2. BEGIN(트랜잭션 시작 처리)
                        await client.query('BEGIN');

                        //처리 3초 지연
                        //await client.query('SELECT pg_sleep(3);');

                        //3. [※작업내용1] 데이터 등록
                        let insertResultData = await client.query(
                            `INSERT INTO test_schema.test_table (
                                test_seq
                                , test_title
                                , test_content
                                , test_create_datetime
                                , test_create_user
                            ) VALUES (
                                (SELECT COALESCE(MAX(test_seq), 0) + 1 FROM test_schema.test_table)
                                , $1
                                , $2
                                , current_timestamp
                                , 1
                            ) RETURNING test_seq`,
                            param
                        );
                        //console.log('등록 결과 정보 insertResultData : ', insertResultData);

                        //throw '트랜잭션 처리중 Error 강제로 발생시킴';

                        //4. [※작업내용2] 등록한 데이터 수정
                        let updateResultData = await client.query(
                            `UPDATE
                                test_schema.test_table
                            SET 
                                test_title = 'title transaction update'
                                , test_content = 'content transaction update'
                                , test_update_datetime = current_timestamp 1231ㄱㄹㅇㄴ
                                , test_update_user = 1
                            WHERE
                                test_seq = ${insertResultData.rows[0].test_seq}`
                        );
                        //console.log('수정 결과 정보 updateResultData : ', updateResultData);

                        throw '트랜잭션 처리중 Error 강제로 발생시킴';

                        //5. ＊작업 완료시, 실행(dispatch)한 모든 쿼리를 COMMIT(커밋)
                        let commitResultData = await client.query('COMMIT');
                        //console.log('커밋 결과 정보 commitResultData : ', commitResultData);

                        //Promise resolve 처리
                        resolve(commitResultData);
                    } catch (error) {
                        //5. ＊에러 완료시, 실행(dispatch)한 모든 쿼리를 COMMIT(커밋)
                        let rollbackResultData = await client.query('ROLLBACK');
                        console.log('롤백 결과 정보 rollbackResultData : ', rollbackResultData);

                        //Promise reject 처리
                        reject(error);
                    } finally {
                        //6. 받아온 connection pool 반납(해제) - 필수(반납하지 않으면 해당 pool을 사용하는 query는 트랜잭션에 계속 매달려있음)
                        client.release();
                    }
                }).catch((error) => {
                    //Promise reject 처리
                    reject(error);
                })
            });
        },//testTransaction() end

    }//return end
 
 })();
 
 //Module Export
 module.exports = TestDAO;