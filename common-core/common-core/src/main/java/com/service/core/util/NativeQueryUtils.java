package com.service.core.util;


import com.service.core.exception.CustomDocsParserException;
import com.service.core.model.NativeQueryUtilsModel;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class NativeQueryUtils implements NativeQueryUtilsModel {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    private ClassLoader getServiceClassLoader() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // 스택트레이스를 역순으로 순회하여 실제 서비스 클래스 찾기
        for (int i = stackTrace.length - 1; i >= 0; i--) {
            String className = stackTrace[i].getClassName();

            // common_service가 아닌 서비스 클래스 찾기
            if (!className.contains("com.service.common_service") &&
                    className.contains("com.service")) {
                try {
                    return Class.forName(className).getClassLoader();
                } catch (ClassNotFoundException e) {
                    // 무시하고 계속 진행
                }
            }
        }

        // 찾지 못한 경우 현재 스레드의 컨텍스트 클래스로더 반환
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    public String readXmlQueryFile(String packageName, String queryId) {
        try {
            // XML 파일 경로
            ClassLoader classLoader = getServiceClassLoader();
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("queryPackage/" + packageName + ".xml");
            if (inputStream == null) {
                throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
            }
            // DocumentBuilderFactory 및 DocumentBuilder 생성
            Document doc = getDocument(inputStream);
            // querys 태그 내의 query 태그 리스트 가져오기
            NodeList queryList = doc.getElementsByTagName("query");
            return getNativeQuery(queryId, queryList);
        } catch (Exception e) {
            throw new CustomDocsParserException();
        }

    }

    public String isFindQuery(Node queryNode, String queryId){
        if (queryNode.getNodeType() == Node.ELEMENT_NODE) {
            Element queryElement = (Element) queryNode;
            if (queryElement.getAttribute("id").equals(queryId)) {
                return queryElement.getTextContent();
            }
        }
        return null;
    }

    @Nullable
    private String getNativeQuery(String queryId, NodeList queryList) {
        for (int i = 0; i < queryList.getLength(); i++) {
            String currentQuery = isFindQuery(queryList.item(i), queryId);
            if(currentQuery != null) {
                return currentQuery;
            }
        }
        return null;
    }


    private static Document getDocument(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // XML 파일 파싱
        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        return doc;
    }

    @Override
    public String getQueryString(String packageName, String queryId) {
        return getQueryString(packageName, queryId, null, null);
    }

    @Override
    public String getQueryString(String packageName, String queryId, Map<String, Object> eqParamMap) {
        return getQueryString(packageName, queryId, eqParamMap, null);
    }

    @Override
    public String getQueryString(String packageName, String queryId, Map<String, Object> eqParamMap, Map<String, Object> inParamMap) {
        String queryString = readXmlQueryFile(packageName, queryId);
        if(inParamMap!=null){
            eqParamMap.putAll(getInParamMapToStringMap(inParamMap));
        }

        if(eqParamMap!=null){
            List<String> keyList = getKeyListOrderByLength(eqParamMap);
            for (String key : keyList) {
                queryString = replaceParameter(queryString, key, String.valueOf(eqParamMap.get(key)));
            }
        }
        return queryString;
    }

    @Override
    public Map<String, Object> getInParamMapToStringMap(Map<String, Object> inParamMap) {
        Map<String, Object> refineParamMap = new HashMap<>();
        inParamMap.forEach((key, value)->{
            String convertedValue = String.valueOf(value);
            if(convertedValue.equals("null") || convertedValue.isEmpty()){
                throw new IllegalArgumentException("In 절의 파라미터로는 빈 문자열 혹은 null을 사용할 수 없습니다");
            }
            refineParamMap.put(key, getSplitString(value));
        });
        return refineParamMap;
    }

    private String getSplitString(Object value) {
        StringBuilder sb = new StringBuilder();
        Object[] refineObject = null;
        if (value instanceof Collection<?>){
            refineObject = ((Collection<?>) value).toArray();
        } else if(value.getClass().isArray()){
            refineObject = (Object[]) value;
        } else if(value instanceof String){
            refineObject = ((String) value).split(",");
        } else {
            throw new IllegalArgumentException("In 절의 파라미터는 Collection<String>, String[], String만 가능합니다");
        }
        for(Object param : refineObject){
            sb.append("'").append(param).append("'").append(",");
        }
        return sb.substring(0,sb.length()-1);
    }

    @Override
    public String replaceParameter(String queryString, String parameter, String value) {
        return queryString.replaceAll(":" + parameter, value==null || value.isEmpty() ? "null" : "'" + value + "'" );
    }

    @Override
    public List<String> getKeyListOrderByLength(Map<String, Object> paramMap) {
        return paramMap.keySet().stream().sorted((o1,o2)-> Integer.compare(o2.length(), o1.length())).toList();
    }

    @Override
    public List getSelectQueryResults(String packageName, String queryId) {
        return getSelectQueryResults(packageName, queryId, null, (Map<String, Object>) null);
    }

    @Override
    public Object getSelectQueryResult(String packageName, String queryId) {
        return getSelectQueryResult(packageName, queryId, null, (Map<String, Object>) null);
    }

    @Override
    public <T> T getSelectQueryResult(String packageName, String queryId, Class<T> clazz) {
        return getSelectQueryResult(packageName, queryId, null, null, clazz);
    }

    @Override
    public <T> List<T> getSelectQueryResults(String packageName, String queryId, Class<T> clazz) {
        return getSelectQueryResults(packageName, queryId, null, null, clazz);
    }

    @Override
    public Object getSelectQueryResult(String packageName, String queryId, Map<String, Object> eqParamMap) {
        return getSelectQueryResult(packageName, queryId, eqParamMap, (Map<String, Object>) null);

    }

    @Override
    public List getSelectQueryResults(String packageName, String queryId, Map<String, Object> eqParamMap) {
        return getSelectQueryResults(packageName, queryId, eqParamMap, (Map<String, Object>) null);
    }

    @Override
    public <T> T getSelectQueryResult(String packageName, String queryId, Map<String, Object> eqParamMap, Class<T> clazz) {
        return getSelectQueryResult(packageName, queryId, eqParamMap, null,clazz);
    }

    @Override
    public <T> List<T> getSelectQueryResults(String packageName, String queryId, Map<String, Object> eqParamMap, Class<T> clazz) {
        return getSelectQueryResults(packageName, queryId, eqParamMap, null,clazz);
    }

    @Override
    public List getSelectQueryResults(String packageName, String queryId, Map<String, Object> eqParamMap, Map<String, Object> inParamMap) {
        List<Map<String, Object>> maps;
        maps = namedParameterJdbcTemplate.queryForList(getQueryString(packageName, queryId, eqParamMap, inParamMap),
                Map.of());
        return maps;
    }

    @Override
    public Object getSelectQueryResult(String packageName, String queryId, Map<String, Object> eqParamMap, Map<String, Object> inParamMap) {
        List<Map<String, Object>> maps;
        maps = namedParameterJdbcTemplate.queryForList(getQueryString(packageName, queryId, eqParamMap, inParamMap),
                Map.of());
        return maps.isEmpty() ? null : maps.getFirst();
    }

    @Override
    public <T> T getSelectQueryResult(String packageName, String queryId, Map<String, Object> eqParamMap, Map<String, Object> inParamMap, Class<T> clazz) {
        List<T> maps = getSelectQueryResults(
                packageName, queryId, eqParamMap, inParamMap, clazz
        );
        return maps.isEmpty() ? null : maps.getFirst();
    }

    @Override
    public <T> List<T> getSelectQueryResults(String packageName, String queryId, Map<String, Object> eqParamMap, Map<String, Object> inParamMap, Class<T> clazz) {
        List<T> maps;
        maps = namedParameterJdbcTemplate.queryForList(getQueryString(packageName, queryId, eqParamMap, inParamMap),
                Map.of(), clazz);
        return maps;
    }

    @Override
    public <T> List<T> getQueryResultListByQueryString(String queryString, Class<T> clazz) {
        return jdbcTemplate.queryForList(queryString, clazz);
    }

    @Override
    public List<Map<String, Object>> getQueryResultListByQueryString(String queryString) {
        return jdbcTemplate.queryForList(queryString);
    }

    @Override
    public Object getQueryResultByQueryString(String queryString, Class<?> clazz) {
        return jdbcTemplate.queryForObject(queryString, clazz);
    }

    @Override
    public int executeQueryString(String queryString, List<Map<String, ?>> paramMaps) {
        MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[paramMaps.size()];
        int index = 0;
        SqlParameterSource[] sqlParameterSource = getSqlParameterArray(paramMaps, mapSqlParameterSources, index);
        return namedParameterJdbcTemplate.batchUpdate(queryString,sqlParameterSource).length;
    }

    private SqlParameterSource[] getSqlParameterArray(List<Map<String, ?>> paramMaps, MapSqlParameterSource[] mapSqlParameterSources, int index) {
        for(Map<String, ?> paramMap : paramMaps){
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            paramMap.forEach((key,value)->{
                mapSqlParameterSource.addValue(key, getNullSafetyValue(value));
            });
            mapSqlParameterSources[index++] = mapSqlParameterSource;
        }
        return mapSqlParameterSources;
    }

    private Object getNullSafetyValue(Object value) {
        return value != null ? value : "null";
    }


}
