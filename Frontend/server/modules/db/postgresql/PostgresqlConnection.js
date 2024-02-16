/**
 * @author : 최정우
 * @since : 2022.09.20
 * @dscription : PostgreSQL DB Connection Pool 생성 관리 모듈 입니다. (private와 public object 환경 구성)
 */
const PostgresqlConnection = (function () {

    //PostgreSQL DB Connection 라이브러리 모듈
    const pg = require('pg');

    //Connection Pool 객체 - private object(변수)
    const connectionPool = new pg.Pool({
        //host: 'localhost',
        host: '192.168.0.250',
        user: 'test_user',
        password: '1234',
        database: 'test_db',
        ssl: false,
        port: 5432,
        max: 10,
    });

    //public object
    return {
        getConnectionPool: function () {
            return connectionPool;
        },
        getConnectionPoolClient: function () {
            return connectionPool.connect();
        },
        queryExcute: function (sql, params) {
            return connectionPool.query(sql, params);
        },
    }

})();

//Module Export
module.exports = PostgresqlConnection;


