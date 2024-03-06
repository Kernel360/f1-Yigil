package kr.co.yigil.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminAttachFile is a Querydsl query type for AdminAttachFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminAttachFile extends EntityPathBase<AdminAttachFile> {

    private static final long serialVersionUID = 1633650537L;

    public static final QAdminAttachFile adminAttachFile = new QAdminAttachFile("adminAttachFile");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final EnumPath<FileType> fileType = createEnum("fileType", FileType.class);

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originalFileName = createString("originalFileName");

    public QAdminAttachFile(String variable) {
        super(AdminAttachFile.class, forVariable(variable));
    }

    public QAdminAttachFile(Path<? extends AdminAttachFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminAttachFile(PathMetadata metadata) {
        super(AdminAttachFile.class, metadata);
    }

}

