package club.kwcoder.server.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * course_content
 * @author 
 */
@Data
public class CourseContentDO implements Serializable {
    /**
     * 课程id
     */
    private String id;

    /**
     * 课程内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}