package club.kwcoder.server.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * course_content_file
 * @author 
 */
@Data
public class CourseContentFileDO implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 地址
     */
    private String url;

    /**
     * 文件名
     */
    private String name;

    /**
     * 大小|字节b
     */
    private Integer size;

    private static final long serialVersionUID = 1L;
}