/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트용 Router(Controller) 모듈 입니다.
 */
const express = require('express');
const router = express.Router();

//Service
const TestService = require('../model/TestService');


/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트 데이터 목록 조회(POST)
 */
router.get('/testSelectList.json', function (request, response, next) {
    //Callback 함수 Parameter result = {data: Object, error: Object}
    TestService.testSelectList((result) => {
        if (!result.error) {
            response.send(result.data);
        } else {
            next(result.error);//Global Error Handler에게 예외상황 맡기기
        }
    });
})

/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트 데이터 상세 조회(POST)
 */
router.get('/testSelectOne.json', function (request, response, next) {
    //Callback 함수 Parameter -> result = {data: Object, error: Object}
    TestService.testSelectOne((result) => {
        if (!result.error) {
            response.send(result.data);
        } else {
            next(result.error);//Global Error Handler에게 예외상황 맡기기
        }
    });
})

/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트 데이터 등록 (POST)
 */
router.get('/testInsertOne.json', function (request, response, next) {
    //Callback 함수 Parameter -> result = {data: Object, error: Object}
    TestService.testInsertOne((result) => {
        if (!result.error) {
            response.send(result.data);
        } else {
            next(result.error);//Global Error Handler에게 예외상황 맡기기
        }
    })
})

/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트 데이터 수정 (POST)
 */
router.get('/testUpdateOne.json', function (request, response, next) {
    //Callback 함수 Parameter -> result = {data: Object, error: Object}
    TestService.testUpdateOne((result) => {
        if (!result.error) {
            response.send(result.data);
        } else {
            next(result.error);//Global Error Handler에게 예외상황 맡기기
        }
    })
})

/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : 테스트 데이터 삭제 (POST)
 */
router.get('/testDeleteOne.json', function (request, response, next) {
    //Callback 함수 Parameter -> result = {data: Object, error: Object}
    TestService.testDeleteOne((result) => {
        if (!result.error) {
            response.send(result.data);
        } else {
            next(result.error);//Global Error Handler에게 예외상황 맡기기
        }
    })
})

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
router.get('/testTransaction.json', function (request, response, next) {
    //Callback 함수 Parameter -> result = {data: Object, error: Object}
    TestService.testTransaction((result) => {
        if (!result.error) {
            response.send(result.data);
        } else {
            next(result.error);//Global Error Handler에게 예외상황 맡기기
        }
    })
})

//Module Export
module.exports = router;