package com.service.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAuth is a Querydsl query type for UserAuth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAuth extends EntityPathBase<UserAuth> {

    private static final long serialVersionUID = -435484511L;

    public static final QUserAuth userAuth = new QUserAuth("userAuth");

    public final com.service.core.entity.QCmnBaseCUDEntity _super = new com.service.core.entity.QCmnBaseCUDEntity(this);

    public final StringPath comCd = createString("comCd");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createUser = _super.createUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deleteUser = _super.deleteUser;

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateUser = _super.updateUser;

    public final StringPath userId = createString("userId");

    public final StringPath userPassword = createString("userPassword");

    //inherited
    public final StringPath useYn = _super.useYn;

    public QUserAuth(String variable) {
        super(UserAuth.class, forVariable(variable));
    }

    public QUserAuth(Path<? extends UserAuth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAuth(PathMetadata metadata) {
        super(UserAuth.class, metadata);
    }

}

