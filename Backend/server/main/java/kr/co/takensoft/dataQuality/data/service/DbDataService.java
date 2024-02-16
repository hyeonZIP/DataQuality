package kr.co.takensoft.dataQuality.data.service;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.co.takensoft.dataQuality.data.dao.DbDataDAO;
import kr.co.takensoft.dataQuality.data.dao.SchemaDataDAO;
import kr.co.takensoft.dataQuality.rule.dao.DomainRuleManagementDAO;
import kr.co.takensoft.dataQuality.schedule.dao.ManageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

@Service
public class DbDataService {
    @Autowired
    private DbDataDAO dbDataDAO;
    @Autowired
    private SchemaDataDAO schemaDataDAO;
    @Autowired
    private DomainRuleManagementDAO domainRuleManagementDAO;
    @Autowired
    private ManageDAO manageDAO;

    /**
     * DB 데이터 select.
     *
     * @author 박민혁
     * @since 2024.01.17
     */
    public List<HashMap<String, Object>> selectDbList(HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);

        return dbDataDAO.selectDbList(params);
    }

    /**
     * DB 데이터 insert.
     *
     * @author 박민혁
     * @since 2024.01.17
     */
    public int insertDbData(HashMap<String, Object> params) throws Exception {
        return dbDataDAO.insertDbData(params);
    }

    /**
     * DB 연결. 성공 시 연결 상태가 1로 실패 시 0로
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public int connectDb(HashMap<String, Object> params) throws Exception {
        deleteSchemaData(params); // 연결된 DB의 스키마 데이터를 전부 삭제하고서 다시 입력
        manageDAO.deleteManageRuleAll(); // 연결된 진단 규칙도 삭제

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName((String) params.get("dbms_drive_nm"));
        hikariConfig.setJdbcUrl((String) params.get("dbms_url_port"));
        hikariConfig.setUsername((String) params.get("dbms_connect_id"));
        hikariConfig.setPassword((String) params.get("dbms_connect_pw"));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        int result = 0;
        String dbmsVersion = "";
        try {
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                dbmsVersion = metaData.getDatabaseProductVersion();

                String schemaQuery ="SELECT schema_name FROM information_schema.schemata WHERE schema_name NOT IN ('information_schema', 'pg_catalog', 'pg_toast') ORDER BY schema_name ASC";
                try (Statement statement = connection.createStatement();
                     ResultSet schemaResult = statement.executeQuery(schemaQuery)) {

                    while (schemaResult.next()) {
                        String schemaName = schemaResult.getString("schema_name");
                        params.put("schema_name", schemaName);
                        dbDataDAO.collectSchema(params);
                    }
                    result = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // HikariCP 데이터 소스 종료
            if (dataSource != null) {
                dataSource.close();
            }
        }
        params.put("dbms_connect", result);
        params.put("dbms_version", dbmsVersion);
        dbDataDAO.connectDb(params);

        return result;
    }
    /**
     * DB의 스키마 정보 수집.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public int collectSchema(HashMap<String, Object> params) throws Exception {
        deleteTableData(params);
        manageDAO.deleteManageRuleAll();

        List<HashMap<String, Object>> schemaList = schemaDataDAO.selectSchemaListYN(params);

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName((String) params.get("dbms_drive_nm"));
        hikariConfig.setJdbcUrl((String) params.get("dbms_url_port"));
        hikariConfig.setUsername((String) params.get("dbms_connect_id"));
        hikariConfig.setPassword((String) params.get("dbms_connect_pw"));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        int result = 0;

        try {
            for (HashMap<String, Object> schema : schemaList) {
                String schemaName = (String) schema.get("schema_name");
                // 스키마의 테이블 정보 입력
                try (Connection connection = dataSource.getConnection()) {
                    String tableQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schemaName + "'";
                    try (Statement tableStatement = connection.createStatement();
                         ResultSet tableResultSet = tableStatement.executeQuery(tableQuery)) {

                        while (tableResultSet.next()) {
                            String tableName = tableResultSet.getString("table_name");
                            params.put("table_nm", tableName);
                            params.put("schema_id", (Integer) schema.get("schema_id"));

                            dbDataDAO.insertTableList(params);
                            // 테이블의 컬럼 정보 입력
                            String columnQuery = "SELECT column_name, data_type, is_nullable FROM information_schema.columns WHERE table_name = '" + tableName + "' AND table_schema = '" + schemaName + "'";
                            try (Statement columnStatement = connection.createStatement();
                                 ResultSet columnResultSet = columnStatement.executeQuery(columnQuery)) {

                                while (columnResultSet.next()) {
                                    String columnName = columnResultSet.getString("column_name");
                                    String dataType = columnResultSet.getString("data_type");
                                    String isNullable = columnResultSet.getString("is_nullable");
                                    HashMap<String, Object> tableId = dbDataDAO.selectTableIndex(params);

                                    // 컬럼 정보를 params에 설정
                                    params.put("column_nm", columnName);
                                    params.put("column_type", dataType);
                                    params.put("column_isnull", isNullable);
                                    params.put("table_id", tableId.get("table_id"));

                                    // COLUMN_DATA 테이블에 데이터 삽입
                                    dbDataDAO.insertColumnList(params);

                                    // 동시에 도메인 진단 가능하도록 도메인 데이터 삽입
                                    domainRuleManagementDAO.insertDomainRule(params);
                                }
                            }
                        }
                    }
                    result = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // HikariCP 데이터 소스 종료
            if (dataSource != null) {
                dataSource.close();
            }
        }

        return result;
    }

    /**
     * DB 데이터 delete하며 동시에 연결된 스키마 data도 delete.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public int deleteDbData(HashMap<String, Object> params) throws Exception{
        return dbDataDAO.deleteDbData(params);
    }

    /**
     * DB 데이터 update.
     *
     * @author 박민혁
     * @since 2024.01.22
     */
    public int updateDbData(HashMap<String, Object> params) throws Exception{
        deleteSchemaData(params); // DB 정보가 수정되면 다시 연결이 되야 하기 때문에 정보 삭제
        manageDAO.deleteManageRuleAll();

        return dbDataDAO.updateDbData(params);
    }

    /**
     * 스키마 data delete.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public void deleteSchemaData(HashMap<String, Object> params) throws Exception{
       dbDataDAO.deleteSchemaData(params);
    }

    /**
     * DB 목록 COUNT.
     *
     * @author 박민혁
     * @since 2024.01.19
     */
    public int selectDbCount(HashMap<String, Object> params) throws Exception{
        return dbDataDAO.selectDbCount(params);
    }

    /**
     * 테이블 data delete.
     *
     * @author 박민혁
     * @since 2024.01.18
     */
    public void deleteTableData(HashMap<String, Object> params) throws Exception{
        dbDataDAO.deleteTableData(params);
    }
}