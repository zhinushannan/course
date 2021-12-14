package club.kwcoder.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: zhinushannan
 * @create: 2021/12/14  14:16
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionPageDTO extends PageDTO<SectionDTO> {

    private String courseId;

    private String chapterId;

}
