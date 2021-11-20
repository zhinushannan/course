package club.kwcoder.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: zhinushannan
 * @create: 2021/11/20  19:24
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultBean<T> {

    private Boolean success = true;

    private String code;

    private String message;

    private T data;

    public static <T> ResultBean<T> getSuccess(String message, T data) {
        return new ResultBean<T>(true, "200", message, data);
    }

}
