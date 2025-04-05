package com.service.account_manage.entity.id;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountManagerId is a Querydsl query type for AccountManagerId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAccountManagerId extends BeanPath<AccountManagerId> {

    private static final long serialVersionUID = -966740620L;

    public static final QAccountManagerId accountManagerId = new QAccountManagerId("accountManagerId");

    public final StringPath comCd = createString("comCd");

    public final StringPath transactionId = createString("transactionId");

    public final StringPath userId = createString("userId");

    public QAccountManagerId(String variable) {
        super(AccountManagerId.class, forVariable(variable));
    }

    public QAccountManagerId(Path<? extends AccountManagerId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountManagerId(PathMetadata metadata) {
        super(AccountManagerId.class, metadata);
    }

}

