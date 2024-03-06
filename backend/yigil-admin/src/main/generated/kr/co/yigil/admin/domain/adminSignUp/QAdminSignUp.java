package kr.co.yigil.admin.domain.adminSignUp;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminSignUp is a Querydsl query type for AdminSignUp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminSignUp extends EntityPathBase<AdminSignUp> {

    private static final long serialVersionUID = -1920851004L;

    public static final QAdminSignUp adminSignUp = new QAdminSignUp("adminSignUp");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public QAdminSignUp(String variable) {
        super(AdminSignUp.class, forVariable(variable));
    }

    public QAdminSignUp(Path<? extends AdminSignUp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminSignUp(PathMetadata metadata) {
        super(AdminSignUp.class, metadata);
    }

}

