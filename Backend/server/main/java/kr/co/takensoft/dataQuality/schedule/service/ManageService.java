package kr.co.takensoft.dataQuality.schedule.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.co.takensoft.dataQuality.data.dao.DbDataDAO;
import kr.co.takensoft.dataQuality.schedule.dao.ManageDAO;
import kr.co.takensoft.dataQuality.result.dao.ManageResultDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class ManageService {
    @Autowired
    private ManageDAO manageDAO;

    @Autowired
    private DbDataDAO dbDataDAO;

    @Autowired
    private ManageResultDAO manageResultDAO;

    /**
     * 총합 규칙 관리 select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public List<HashMap<String, Object>> selectManageRule (HashMap<String, Object> params) throws Exception{
        int perPage = (int)params.get("perPage");
        int currentPage = (int)params.get("currentPage");
        int startIndex = (currentPage-1)*perPage;
        params.put("startIndex", startIndex);
        return manageDAO.selectManageRule(params);
    }

    /**
     * 총합 규칙 관리 count select.
     *
     * @author 박민혁
     * @since 2024.01.23
     */
    public int selectManageRuleCount (HashMap<String, Object> params) throws Exception{
        return manageDAO.selectManageRuleCount(params);
    }

    /*
     * 진단 규칙 실행
     *
     * @author 박민혁
     * @since 2024.02.02
     */
    public int execManageRule (List<HashMap<String, Object>> paramList) throws Exception{
        for (HashMap<String, Object> params : paramList) {
            HashMap<String, Object> dbData = dbDataDAO.selectDbData(params);
            HashMap<String, Object> resultData = new HashMap<>();

            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setDriverClassName((String) dbData.get("dbms_drive_nm"));
            hikariConfig.setJdbcUrl((String) dbData.get("dbms_url_port"));
            hikariConfig.setUsername((String) dbData.get("dbms_connect_id"));
            hikariConfig.setPassword((String) dbData.get("dbms_connect_pw"));

            HikariDataSource dataSource = new HikariDataSource(hikariConfig);

            try {
                try (Connection connection = dataSource.getConnection()) {
                    int resultCnt = getCountAllColumns(connection, params);
                    System.out.println("전체 진단 : " + resultCnt);
                    int errorCnt = getCountRuleFormat(connection, params);
                    System.out.println("오류 있는 행의 수 : " + (resultCnt - errorCnt));
                    String errorNameList = getNameRuleFormat(connection, params);
                    System.out.println("오류 컬럼들 : " + errorNameList);

                    resultData.put("result_cnt", resultCnt);
                    resultData.put("error_cnt", resultCnt - errorCnt);
                    resultData.put("error_nm", errorNameList);
                    resultData.put("manage_rule_id", params.get("manage_rule_id"));

                    manageResultDAO.insertManageResult(resultData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            } finally {
                if (dataSource != null) {
                    dataSource.close();
                }
            }
        }
        return 0;

    }

    /*
     * 진단 규칙 실행 시 전부 컬럼 숫자 확인
     *
     * @author 박민혁
     * @since 2024.02.02
     */
    private int getCountAllColumns(Connection connection, HashMap<String, Object> params) throws Exception {
        String schemaName = (String) params.get("schema_name");
        String tableName = (String) params.get("table_nm");

        String query = "SELECT COUNT(*) FROM " + schemaName + "." + tableName;
        try (Statement preparedStatement = connection.createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(query)) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
     * 진단 규칙 실행 시 진단 규칙에 따른 컬럼 숫자 확인
     *
     * @author 박민혁
     * @since 2024.02.02
     */
    private int getCountRuleFormat(Connection connection, HashMap<String, Object> params) throws Exception {
        String schemaName = (String) params.get("schema_name");
        String tableName = (String) params.get("table_nm");
        String colName = (String) params.get("column_nm");
        String ruleType = (String) params.get("rule_type");
        String query = null;

        if(ruleType.equals("domain_val")){
            String valRuleType = (String) params.get("val_rule_type");
            String valRuleDetail = (String) params.get("val_rule_detail");
            if(valRuleType.equals("날짜")){
                query = "SELECT COUNT(*) FROM " + schemaName + "." + tableName + " WHERE TO_CHAR(" + colName + ", '" + valRuleDetail + "') = " + colName + "::text";
            } else if(valRuleType.equals("형식")){
                query = "SELECT COUNT(*) FROM " + schemaName + "." + tableName + " WHERE " + colName + " ~ '" + valRuleDetail + "'";
            } else if(valRuleType.equals("필수값")){
                query = "SELECT COUNT(*) FROM " + schemaName + "." + tableName + " WHERE " + colName + " IS " + valRuleDetail;
            } else if(valRuleType.equals("범위")){
                valRuleDetail = valRuleDetail.replace("컬럼" , colName);
                query = "SELECT COUNT(*) FROM " + schemaName + "." + tableName + " WHERE " + valRuleDetail;
            } else if(valRuleType.equals("여부")){
                String[] values = valRuleDetail.split(",");

                StringBuilder ruleDetails = new StringBuilder(" ");
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        ruleDetails.append(" OR ");
                    }
                    ruleDetails.append(colName + " = ").append(values[i]);
                }
                query = "SELECT COUNT(*) FROM " + schemaName + "." + tableName + " WHERE " + ruleDetails;
            }
        } else if(ruleType.equals("domain_code")){

        } else if(ruleType.equals("ref_rule")){

        } else if(ruleType.equals("work_rule")){

        } else if(ruleType.equals("dupl_rule")){

        }

        try (Statement preparedStatement = connection.createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(query)) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    /*
     * 진단 규칙 실행 시 진단 규칙에 따른 컬럼 이름 확인
     *
     * @author 박민혁
     * @since 2024.02.02
     */
    private String getNameRuleFormat(Connection connection, HashMap<String, Object> params) throws Exception {
        String schemaName = (String) params.get("schema_name");
        String tableName = (String) params.get("table_nm");
        String colName = (String) params.get("column_nm");
        String ruleType = (String) params.get("rule_type");
        String query = null;

        List<HashMap<String, Object>> result = new ArrayList<>();

        if(ruleType.equals("domain_val")){
            String valRuleType = (String) params.get("val_rule_type");
            String valRuleDetail = (String) params.get("val_rule_detail");
            if(valRuleType.equals("날짜")){
                query = "SELECT " + colName + " FROM " + schemaName + "." + tableName + " WHERE " + colName + " IS NULL OR NOT (TO_CHAR(" + colName + ", '" + valRuleDetail + "') = " + colName + "::text)";
            } else if(valRuleType.equals("형식")){
                query = "SELECT " + colName + " FROM " + schemaName + "." + tableName + " WHERE " + colName + " IS NULL OR NOT (" + colName + " ~ '" + valRuleDetail + "')";
            } else if(valRuleType.equals("필수값")){
                query = "SELECT " + colName + " FROM " + schemaName + "." + tableName + " WHERE NOT " + colName + " IS " + valRuleDetail;
            } else if(valRuleType.equals("범위")){
                valRuleDetail = valRuleDetail.replace("컬럼" , colName);
                query = "SELECT " + colName + " FROM " + schemaName + "." + tableName + " WHERE " + colName + " IS NULL OR NOT (" + valRuleDetail + ")";
            } else if(valRuleType.equals("여부")){
                String[] values = valRuleDetail.split(",");

                StringBuilder ruleDetails = new StringBuilder(" ");
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        ruleDetails.append(" OR ");
                    }
                    ruleDetails.append(colName + " = ").append(values[i]);
                }
                query = "SELECT " + colName + " FROM " + schemaName + "." + tableName + " WHERE " + colName + " IS NULL OR NOT (" + ruleDetails + ")";
            }
        } else if(ruleType.equals("domain_code")){

        } else if(ruleType.equals("ref_rule")){

        } else if(ruleType.equals("work_rule")){

        } else if(ruleType.equals("dupl_rule")){

        }
        StringBuilder rowData = new StringBuilder();

        try (Statement preparedStatement = connection.createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    Object columnValue = resultSet.getObject(i);
                    if (columnValue != null) {
                        rowData.append(columnValue.toString() + ", ");
                    }
                }
            }
            if (rowData.length() > 2 && rowData.substring(rowData.length() - 2).equals(", ")) {
                rowData.delete(rowData.length() - 2, rowData.length());
            }
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return rowData.toString();
    }
}
