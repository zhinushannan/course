package club.kwcoder.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhinushannan
 * @create: 2021/12/14  14:16
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterPageDTO extends PageDTO<ChapterDTO> {

    private String courseId;

}
