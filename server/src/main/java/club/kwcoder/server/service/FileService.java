package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.FileDO;
import club.kwcoder.server.dataobject.FileDOExample;
import club.kwcoder.server.dto.FileDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.FileMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class FileService {

    @Resource
    private FileMapper fileMapper;

    /**
     * 列表查询
     */
    public void list(PageDTO<FileDTO> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        FileDOExample fileExample = new FileDOExample();
        List<FileDO> fileList = fileMapper.selectByExample(fileExample);
        PageInfo<FileDO> pageInfo = new PageInfo<>(fileList);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDTO> fileDtoList = CopyUtil.copyList(fileList, FileDTO.class);
        pageDto.setData(fileDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(FileDTO fileDto) {
        FileDO file = CopyUtil.copy(fileDto, FileDO.class);
        if (StringUtils.isEmpty(fileDto.getId())) {
            this.insert(file);
        } else {
            this.update(file);
        }
    }

    /**
     * 新增
     */
    private void insert(FileDO file) {
        Date now = new Date();
        file.setCreatedAt(now);
        file.setUpdatedAt(now);
        file.setId(UuidUtil.getShortUuid());
        fileMapper.insert(file);
    }

    /**
     * 更新
     */
    private void update(FileDO file) {
        file.setUpdatedAt(new Date());
        fileMapper.updateByPrimaryKey(file);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }
}
