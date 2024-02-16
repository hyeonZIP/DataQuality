package kr.co.takensoft.dataQuality.data.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.co.takensoft.dataQuality.data.dao.ColumnDataDAO;
import kr.co.takensoft.dataQuality.data.dao.DbDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ColumnDataService {
    @Autowired
    private ColumnDataDAO columnDataDAO;
    @Autowired
    private DbDataDAO dbDataDAO;

    /**
     * 검색결과 테이블 조회
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> selectSearchResult (HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex", startIndex);
        return columnDataDAO.selectSearchResult(params);
    }
    /**
     * DBMS 옵션 선택
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> selectDbms (HashMap<String, Object> params) throws Exception {
        return columnDataDAO.selectDbms(params);
    }
    /**
     * 스키마 옵션 선택
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public List<HashMap<String, Object>> selectSchema (HashMap<String, Object> params) throws Exception {
        return columnDataDAO.selectSchema(params);
    }
    /**
     * 검색결과 테이블 COUNT
     *
     * @author 임재현
     * @since 2024.01.24
     */
    public int searchResultCount (HashMap<String,Object> params) throws Exception{
        return columnDataDAO.searchResultCount(params);
    }

    /**
     * 테이블 컬럼 조회
     *
     * @author 임재현
     * @since 2024.01.25
     */
    public List<HashMap<String, Object>> selectTableColumn (HashMap<String, Object> params) throws Exception {
        int currentPage = (int) params.get("currentPage");
        int perPage = (int) params.get("perPage");
        int startIndex = (currentPage - 1) * perPage;
        params.put("startIndex1", startIndex);
        return columnDataDAO.selectTableColumn(params);
    }
    /**
     * 테이블 컬럼 COUNT
     *
     * @author 임재현
     * @since 2024.01.25
     */
    public int tableColumnCount (HashMap<String,Object> params) throws Exception{
        return columnDataDAO.tableColumnCount(params);
    }

    /**
     * 컬럼 분석
     *
     * @author 박민혁
     * @since 2024.01.26
     */
    public int analyzeColumn(HashMap<String,Object> params) throws Exception{
        List<HashMap<String, Object>> resultData = new ArrayList<>();
        HashMap<String, Object> dbData = dbDataDAO.selectDbData(params);

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName((String) dbData.get("dbms_drive_nm"));
        hikariConfig.setJdbcUrl((String) dbData.get("dbms_url_port"));
        hikariConfig.setUsername((String) dbData.get("dbms_connect_id"));
        hikariConfig.setPassword((String) dbData.get("dbms_connect_pw"));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        // 컬럼 데이터 전부 가져오기
        try {
            try (Connection connection = dataSource.getConnection()) {
                String table_nm = (String) params.get("table_nm");
                String schema_name = (String) params.get("schema_name");
                String query = "SELECT * FROM " + schema_name + "." + table_nm;
                try (Statement tableStatement = connection.createStatement();
                     ResultSet tableResultSet = tableStatement.executeQuery(query)) {

                    ResultSetMetaData metaData = tableResultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (tableResultSet.next()) {
                        HashMap<String, Object> rowMap = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            Object columnValue = tableResultSet.getObject(i);
                            rowMap.put(columnName, columnValue);
                        }
                        resultData.add(rowMap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        // 컬럼 데이터 정리 후 insert
        resultData = arrangeAnalyzeCol(resultData);
        List<HashMap<String, Object>> columnData = columnDataDAO.selectTableColumnNM(params);

        for (HashMap<String, Object> column : columnData) {
            String columnName = (String) column.get("column_nm");
            int columnId = (int) column.get("column_id");

            // 이전에 존재하는 column 분석 정보 삭제
            columnDataDAO.deleteColumnAnl(column);

            for (HashMap<String, Object> data : resultData) {
                if (data.containsKey("columnName") && data.get("columnName").equals(columnName)) {
                    List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) data.get("data");
                    for (HashMap<String, Object> item : dataList) {
                        Object columnValue = item.get("value");
                        int count = (int) item.get("count");

                        HashMap<String, Object> param = new HashMap<>();
                        param.put("column_anl_value", columnValue);
                        param.put("column_anl_count", count);
                        param.put("column_id", columnId);

                        columnDataDAO.insertColumnAnl(param);
                    }
                    break;
                }
            }
        }
        return 0;
    }

    /**
     * 컬럼 분석 데이터 정리 / 값이 null이면 "null" 문자로 저장
     *
     * @author 박민혁
     * @since 2024.01.26
     */
    public List<HashMap<String, Object>> arrangeAnalyzeCol(List<HashMap<String, Object>> analyzedColumnList) {
        List<HashMap<String, Object>> arrangedColumns = new ArrayList<>();


        for (HashMap<String, Object> data : analyzedColumnList) {
            for (HashMap.Entry<String, Object> entry : data.entrySet()) {
                String columnName = entry.getKey();
                Object columnValue = entry.getValue();

                int columnIndex = -1;

                for (int i = 0; i < arrangedColumns.size(); i++) {
                    if (columnName.equals(arrangedColumns.get(i).get("columnName"))) {
                        columnIndex = i;
                    }
                }

                if (columnIndex == -1) {
                    HashMap<String, Object> newColumn = new HashMap<>();
                    newColumn.put("columnName", columnName);
                    List<HashMap<String, Object>> dataList = new ArrayList<>();

                    if (columnValue != null) {
                        HashMap<String, Object> valueMap = new HashMap<>();
                        valueMap.put("value", columnValue);
                        valueMap.put("count", 1);
                        dataList.add(valueMap);
                    } else {
                        HashMap<String, Object> valueMap = new HashMap<>();
                        valueMap.put("value", "null");
                        valueMap.put("count", 1);
                        dataList.add(valueMap);
                    }

                    newColumn.put("data", dataList);
                    arrangedColumns.add(newColumn);
                } else {
                    List<HashMap<String, Object>> columnData = (List<HashMap<String, Object>>) arrangedColumns.get(columnIndex).get("data");
                    boolean found = false;

                    if (columnValue != null) {
                        for (HashMap<String, Object> map : columnData) {
                            if (columnValue.equals(map.get("value"))) {
                                int count = (int) map.get("count");
                                map.put("count", count + 1);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            HashMap<String, Object> valueMap = new HashMap<>();
                            valueMap.put("value", columnValue);
                            valueMap.put("count", 1);
                            columnData.add(valueMap);
                        }
                    } else {
                        HashMap<String, Object> valueMap = new HashMap<>();
                        valueMap.put("value", "null");
                        valueMap.put("count", 1);
                        columnData.add(valueMap);
                    }
                }
            }
        }

        return arrangedColumns;
    }

    /**
     * 컬럼 분석 결과 select
     *
     * @author 박민혁
     * @since 2024.01.26
     */
    public List<HashMap<String, Object>> selectColumnAnl(HashMap<String,Object> params) throws Exception{
        return columnDataDAO.selectColumnAnl(params);
    }

}
