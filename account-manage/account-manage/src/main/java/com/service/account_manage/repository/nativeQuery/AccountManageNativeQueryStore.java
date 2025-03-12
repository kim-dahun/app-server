package com.service.account_manage.repository.nativeQuery;

import org.springframework.stereotype.Component;
@Component
public class AccountManageNativeQueryStore {

    public String findByUpdateDate(String updateDate){
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ACCOUNT_MANAGE WHERE 1=1");
        if(updateDate!=null && !updateDate.isEmpty()){
            queryBuilder.append("AND UPDATE_DATE = '").append(updateDate).append("'");
        }


        return queryBuilder.toString();
    }

}
