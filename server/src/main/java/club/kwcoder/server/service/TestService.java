package club.kwcoder.server.service;/**
 * @author zhinushannan
 * @date 2021/11/16 8:03
 * @subject
 */

import club.kwcoder.server.dataobject.TestDO;
import club.kwcoder.server.dataobject.TestDOExample;
import club.kwcoder.server.mapper.TestMapper;
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
