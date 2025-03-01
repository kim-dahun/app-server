package com.service.account_manage.entity.id;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountCodeId is a Querydsl query type for AccountCodeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAccountCodeId extends BeanPath<AccountCodeId> {

    private static final long serialVersionUID = -206073956L;

    public static final QAccountCodeId accountCodeId = new QAccountCodeId("accountCodeId");

    public final StringPath codeId = createString("codeId");

    public final StringPath userId = createString("userId");

    public QAccountCodeId(String variable) {
        super(AccountCodeId.class, forVariable(variable));
    }

    public QAccountCodeId(Path<? extends AccountCodeId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountCodeId(PathMetadata metadata) {
        super(AccountCodeId.class, metadata);
    }

}

