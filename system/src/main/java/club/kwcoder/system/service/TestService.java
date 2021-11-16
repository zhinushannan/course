package club.kwcoder.system.service;/**
 * @author zhinushannan
 * @date 2021/11/16 8:03
 * @subject
 */

import club.kwcoder.system.dataobject.TestDO;
import club.kwcoder.system.dataobject.TestDOExample;
import club.kwcoder.system.mapper.TestMapper;
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
