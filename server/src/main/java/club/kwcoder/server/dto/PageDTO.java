package club.kwcoder.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhinushannan
 * @create: 2021/11/20  14:17
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {

    protected int page;

    protected int size;

    protected long total;

    protected List<T> data;

}
