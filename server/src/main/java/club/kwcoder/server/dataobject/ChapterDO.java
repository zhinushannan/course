package club.kwcoder.server.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * chapter
 * @author 
 */
@Data
public class ChapterDO implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 名称
     */
    private String name;

    private static final long serialVersionUID = 1L;

}