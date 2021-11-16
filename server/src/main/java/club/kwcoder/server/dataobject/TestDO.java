package club.kwcoder.server.dataobject;

import lombok.Data;

import java.io.Serializable;

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