package com.service.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComInfo is a Querydsl query type for ComInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComInfo extends EntityPathBase<ComInfo> {

    private static final long serialVersionUID = -1272856703L;

    public static final QComInfo comInfo = new QComInfo("comInfo");

    public final com.service.core.entity.QCmnBaseCUDEntity _super = new com.service.core.entity.QCmnBaseCUDEntity(this);

    public final StringPath address = createString("address");

    public final StringPath addressDetail = createString("addressDetail");

    public final StringPath addressNo = createString("addressNo");

    public final StringPath comCd = createString("comCd");

    public final StringPath comNm = createString("comNm");

    public final StringPath comSignNo = createString("comSignNo");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createUser = _super.createUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final StringPath deleteUser = _super.deleteUser;

    public final StringPath memberGrade = createString("memberGrade");

    public final StringPath phone = createString("phone");

    public final StringPath phone2 = createString("phone2");

    public final StringPath representative = createString("representative");

    public final StringPath status = createString("status");

    public final StringPath tel = createString("tel");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateUser = _super.updateUser;

    //inherited
    public final StringPath useYn = _super.useYn;

    public QComInfo(String variable) {
        super(ComInfo.class, forVariable(variable));
    }

    public QComInfo(Path<? extends ComInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComInfo(PathMetadata metadata) {
        super(ComInfo.class, metadata);
    }

}

