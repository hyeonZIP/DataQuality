/**
 * @author : 방선주
 * @since : 2022.09.22
 * @dscription : Oracle DB Connection Pool 생성 관리 모듈 입니다.
 * 
 * @modifier : 최정우
 * @modified : 2022.10.30
 */
 const { BASE_DIR } = require('../../../../Global');

 const OracleConnection = function () {
   //오라클 라이브러리 import
   const oracledb = require('oracledb');
   //라이브러리 초기화(oracle client setting)
   oracledb.initOracleClient({ libDir: `${BASE_DIR}/server/modules/db/oracle/client/client_19.16` });
 
   //DB Connection Pool
   let connectionPool = null;
 
   //DB Connection Pool 생성
   oracledb.createPool({
     user: 'ajin_data',
     password: 'ajinvldosvl1121',  // myhrpw contains the hr schema password
     connectString: '(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 211.216.206.147)(PORT = 1521)))(CONNECT_DATA = (SID = XE)))',
     poolMin: 0,
     poolMax: 20,
     //poolAlias: 'ajin_odbcp'//poolAlias가 정의되지 않으면, default pool로 정의됨
   }).then((result) => {
     connectionPool = result;
     console.log("Successfully connected to Oracle Database");
   }).catch((error) => {
     console.error(error);
   });
 
   return {
     getConnectionPool: function () {
       return connectionPool;
     },
     getConnection: function () {
       return oracledb.getConnection();
     },
     queryExcute: function (sql, params) {
       const result = new Promise((resolve, reject) => {
         oracledb.getConnection().then(async (connection) => {
           let queryResult = null;
           if (!params) {
             queryResult = await connection.execute(sql);
           } else {
             queryResult = await connection.execute(sql, params);
           }
           resolve(queryResult);
           connection.close();
         }).catch((error) => {
           reject(error);
         });
       })
       return result;
     },
   }
 }();
 
 module.exports = OracleConnection;