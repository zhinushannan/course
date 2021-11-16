package kwcoder.club.system.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * test
 * @author 
 */
@Data
public class TestDO implements Serializable {
    private String id;

    private String name;

    private static final long serialVersionUID = 1L;
}