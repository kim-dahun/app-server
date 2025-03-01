package com.service.account_manage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountCode is a Querydsl query type for AccountCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountCode extends EntityPathBase<AccountCode> {

    private static final long serialVersionUID = -643546496L;

    public static final QAccountCode accountCode = new QAccountCode("accountCode");

    public final com.service.core.entity.QCmnBaseCUDEntity _super = new com.service.core.entity.QCmnBaseCUDEntity(this);

    public final ListPath<AccountCodeDetail, QAccountCodeDetail> accountCodeDetailList = this.<AccountCodeDetail, QAccountCodeDetail>createList("accountCodeDetailList", AccountCodeDetail.class, QAccountCodeDetail.class, PathInits.DIRECT2);

    public final StringPath codeDesc = createString("codeDesc");

    public final StringPath codeId = createString("codeId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final StringPath userId = createString("userId");

    //inherited
    public final StringPath useYn = _super.useYn;

    public QAccountCode(String variable) {
        super(AccountCode.class, forVariable(variable));
    }

    public QAccountCode(Path<? extends AccountCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountCode(PathMetadata metadata) {
        super(AccountCode.class, metadata);
    }

}

