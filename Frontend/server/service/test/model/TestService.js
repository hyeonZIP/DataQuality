/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트용 서비스 로직을 담당하는 모듈 입니다.
 */
 const TestService = (function () {

    //DAO
    const TestDAO = require('./TestDAO');
    
    //public object
    return {

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 목록 조회
         * @callback : parameter -> {data: Object, error: Object}
         */
        testSelectList: function (callback, param) {
            //Data Access 접근
            let dataAccessResult = TestDAO.testSelectList(param);
            //Data Access 결과
            dataAccessResult.then((data) => {
                console.log('TestService testSelectList connect dataAccessResult : ', data);
                callback({data: data, error: null});//Router에 결과 전달
            }).catch((error) => {
                callback({data: null, error: error});//Router에 Error 전달
            })
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 상세 조회
         */
        testSelectOne: function (callback, param) {
            param = [1];
            //Data Access 접근
            let dataAccessResult = TestDAO.testSelectOne(param);
            //Data Access 결과
            dataAccessResult.then((data) => {
                console.log('TestService testSelectOne connect dataAccessResult : ', data);
                callback({data: data, error: null});//Router에 결과 전달
            }).catch((error) => {
                callback({data: null, error: error});//Router에 Error 전달
            })
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 등록
         */
         testInsertOne: function (callback, param) {
            param = ['test title', 'test content'];
            //Data Access 접근
            let dataAccessResult = TestDAO.testInsertOne(param);
            //Data Access 결과
            dataAccessResult.then((data) => {
                console.log('TestService testInsertOne connect dataAccessResult : ', data.rows[0]['test_seq']);
                callback({data: data, error: null});//Router에 결과 전달
            }).catch((error) => {
                callback({data: null, error: error});//Router에 Error 전달
            })
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 수정
         */
        testUpdateOne: function (callback, param) {
            param = [1, 'test title update', 'test content update'];
            //Data Access 접근
            let dataAccessResult = TestDAO.testUpdateOne(param);
            //Data Access 결과
            dataAccessResult.then((data) => {
                console.log('TestService testUpdateOne connect dataAccessResult : ', data);
                callback({data: data, error: null});//Router에 결과 전달
            }).catch((error) => {
                callback({data: null, error: error});//Router에 Error 전달
            })
        },

        /**
         * @author : 최정우
         * @since : 2022.09.20
         * @dscription : 테스트 데이터 삭제
         */
        testDeleteOne: function (callback, param) {
            param = [10];
            //Data Access 접근
            let dataAccessResult = TestDAO.testDeleteOne(param);
            //Data Access 결과
            dataAccessResult.then((data) => {
                console.log('TestService testDeleteOne connect dataAccessResult : ', data);
                callback({data: data, error: null});//Router에 결과 전달
            }).catch((error) => {
                callback({data: null, error: error});//Router에 Error 전달
            })
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
         testTransaction: function (callback, param) {
            param = ['test title', 'test content'];
            //Data Access 접근
            let dataAccessResult = TestDAO.testTransaction(param);
            //Data Access 결과
            dataAccessResult.then((data) => {
                console.log('TestService testTransaction connect dataAccessResult : ', data);
                callback({data: data, error: null});//Router에 결과 전달
            }).catch((error) => {
                callback({data: null, error: error});//Router에 Error 전달
            })
        },


    }
 
 })();
 
 //Module Export
 module.exports = TestService;
 