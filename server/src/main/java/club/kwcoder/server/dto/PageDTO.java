package club.kwcoder.server.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: zhinushannan
 * @create: 2021/11/20  14:17
 * @description:
 */
@Data
public class PageDTO<T> {

    protected int page;

    protected int size;

    protected long total;

    protected List<T> data;

}
