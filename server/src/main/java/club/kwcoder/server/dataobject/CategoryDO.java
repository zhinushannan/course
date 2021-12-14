package club.kwcoder.server.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * category
 * @author 
 */
@Data
public class CategoryDO implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 父id
     */
    private String parent;

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}