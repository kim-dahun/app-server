package com.service.account_manage.entity.id;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountCodeDetailId is a Querydsl query type for AccountCodeDetailId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAccountCodeDetailId extends BeanPath<AccountCodeDetailId> {

    private static final long serialVersionUID = -556323059L;

    public static final QAccountCodeDetailId accountCodeDetailId = new QAccountCodeDetailId("accountCodeDetailId");

    public final StringPath codeGroup = createString("codeGroup");

    public final StringPath codeId = createString("codeId");

    public final StringPath comCd = createString("comCd");

    public final StringPath userId = createString("userId");

    public QAccountCodeDetailId(String variable) {
        super(AccountCodeDetailId.class, forVariable(variable));
    }

    public QAccountCodeDetailId(Path<? extends AccountCodeDetailId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountCodeDetailId(PathMetadata metadata) {
        super(AccountCodeDetailId.class, metadata);
    }

}

