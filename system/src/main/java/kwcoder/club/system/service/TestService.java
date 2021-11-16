package kwcoder.club.system.service;/**
 * @author zhinushannan
 * @date 2021/11/16 8:03
 * @subject
 */

import kwcoder.club.system.dataobject.TestDO;
import kwcoder.club.system.dataobject.TestDOExample;
import kwcoder.club.system.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhinushannan
 * @create: 2021/11/16  08:03
 * @description:
 */
@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<TestDO> list() {
        return testMapper.selectByExample(new TestDOExample());
    }

}
