package club.kwcoder.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * chapter
 * @author 
 */
@Data
public class ChapterDTO implements Serializable {
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