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
        FileDO fileDO = this.getByKey(fileDto.getKey());
        if (null == fileDO) {
            this.insert(file);
        } else {
            fileDO.setShardIndex(file.getShardIndex());
            this.update(fileDO);
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

    public FileDO getByKey(String key) {
        FileDOExample example = new FileDOExample();
        example.createCriteria().andKeyEqualTo(key);
        List<FileDO> fileDOS = fileMapper.selectByExample(example);
        if (fileDOS.size() > 0) {
            return fileDOS.get(0);
        }
        return null;
    }

    public FileDTO findByKey(String key) {
        return CopyUtil.copy(getByKey(key), FileDTO.class);
    }

}
