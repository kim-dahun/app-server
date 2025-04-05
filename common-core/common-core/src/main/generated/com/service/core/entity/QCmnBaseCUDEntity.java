package com.service.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmnBaseCUDEntity is a Querydsl query type for CmnBaseCUDEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QCmnBaseCUDEntity extends EntityPathBase<CmnBaseCUDEntity> {

    private static final long serialVersionUID = -249023390L;

    public static final QCmnBaseCUDEntity cmnBaseCUDEntity = new QCmnBaseCUDEntity("cmnBaseCUDEntity");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath createUser = createString("createUser");

    public final DateTimePath<java.time.LocalDateTime> deleteDate = createDateTime("deleteDate", java.time.LocalDateTime.class);

    public final StringPath deleteUser = createString("deleteUser");

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final StringPath updateUser = createString("updateUser");

    public final StringPath useYn = createString("useYn");

    public QCmnBaseCUDEntity(String variable) {
        super(CmnBaseCUDEntity.class, forVariable(variable));
    }

    public QCmnBaseCUDEntity(Path<? extends CmnBaseCUDEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmnBaseCUDEntity(PathMetadata metadata) {
        super(CmnBaseCUDEntity.class, metadata);
    }

}

