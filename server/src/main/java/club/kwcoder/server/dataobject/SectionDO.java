package club.kwcoder.server.dataobject;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * section
 * @author 
 */
@Data
public class SectionDO implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 课程|course.id
     */
    private String courseId;

    /**
     * 大章|chapter.id
     */
    private String chapterId;

    /**
     * 视频
     */
    private String video;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 收费|C 收费；F 免费
     */
    private String charge;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * vod|阿里云vod
     */
    private String vod;

    private static final long serialVersionUID = 1L;
}