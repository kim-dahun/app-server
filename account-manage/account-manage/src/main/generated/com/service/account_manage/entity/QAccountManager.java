package com.service.account_manage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountManager is a Querydsl query type for AccountManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountManager extends EntityPathBase<AccountManager> {

    private static final long serialVersionUID = 733859386L;

    public static final QAccountManager accountManager = new QAccountManager("accountManager");

    public final com.service.core.entity.QCmnBaseCUDEntity _super = new com.service.core.entity.QCmnBaseCUDEntity(this);

    public final NumberPath<Double> amount = createNumber("amount", Double.class);

    public final StringPath codeId = createString("codeId");

    public final StringPath comCd = createString("comCd");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final StringPath remark = createString("remark");

    public final StringPath tradeCode = createString("tradeCode");

    public final StringPath tradeType = createString("tradeType");

    public final StringPath transactionDate = createString("transactionDate");

    public final StringPath transactionId = createString("transactionId");

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final StringPath userId = createString("userId");

    //inherited
    public final StringPath useYn = _super.useYn;

    public QAccountManager(String variable) {
        super(AccountManager.class, forVariable(variable));
    }

    public QAccountManager(Path<? extends AccountManager> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountManager(PathMetadata metadata) {
        super(AccountManager.class, metadata);
    }

}

