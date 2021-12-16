package club.kwcoder.server.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * teacher
 * @author 
 */
@Data
public class TeacherDO implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String image;

    /**
     * 职位
     */
    private String position;

    /**
     * 座右铭
     */
    private String motto;

    /**
     * 简介
     */
    private String intro;

    private static final long serialVersionUID = 1L;
}