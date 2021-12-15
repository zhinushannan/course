package club.kwcoder.server.dataobject;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * course_category
 * @author 
 */
@Data
@Accessors(chain = true)
public class CourseCategoryDO implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 课程|course.id
     */
    private String courseId;

    /**
     * 分类|course.id
     */
    private String categoryId;

    private static final long serialVersionUID = 1L;
}