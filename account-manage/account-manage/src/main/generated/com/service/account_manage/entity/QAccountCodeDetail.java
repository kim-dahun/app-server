package com.service.account_manage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountCodeDetail is a Querydsl query type for AccountCodeDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountCodeDetail extends EntityPathBase<AccountCodeDetail> {

    private static final long serialVersionUID = 1456957169L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountCodeDetail accountCodeDetail = new QAccountCodeDetail("accountCodeDetail");

    public final com.service.core.entity.QCmnBaseCUDEntity _super = new com.service.core.entity.QCmnBaseCUDEntity(this);

    public final QAccountCode accountCode;

    public final StringPath codeDesc = createString("codeDesc");

    public final StringPath codeGroup = createString("codeGroup");

    public final StringPath codeId = createString("codeId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath parentCode = createString("parentCode");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final StringPath userId = createString("userId");

    //inherited
    public final StringPath useYn = _super.useYn;

    public QAccountCodeDetail(String variable) {
        this(AccountCodeDetail.class, forVariable(variable), INITS);
    }

    public QAccountCodeDetail(Path<? extends AccountCodeDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountCodeDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountCodeDetail(PathMetadata metadata, PathInits inits) {
        this(AccountCodeDetail.class, metadata, inits);
    }

    public QAccountCodeDetail(Class<? extends AccountCodeDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountCode = inits.isInitialized("accountCode") ? new QAccountCode(forProperty("accountCode")) : null;
    }

}

