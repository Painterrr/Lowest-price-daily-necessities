package com.fisa.lep.choice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChoice is a Querydsl query type for Choice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChoice extends EntityPathBase<Choice> {

    private static final long serialVersionUID = -350352026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChoice choice = new QChoice("choice");

    public final com.fisa.lep.cart.entity.QCart cartId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.fisa.lep.product.entity.QProduct productId;

    public QChoice(String variable) {
        this(Choice.class, forVariable(variable), INITS);
    }

    public QChoice(Path<? extends Choice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChoice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChoice(PathMetadata metadata, PathInits inits) {
        this(Choice.class, metadata, inits);
    }

    public QChoice(Class<? extends Choice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cartId = inits.isInitialized("cartId") ? new com.fisa.lep.cart.entity.QCart(forProperty("cartId"), inits.get("cartId")) : null;
        this.productId = inits.isInitialized("productId") ? new com.fisa.lep.product.entity.QProduct(forProperty("productId"), inits.get("productId")) : null;
    }

}

