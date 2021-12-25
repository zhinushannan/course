package club.kwcoder.server.dataobject;

import lombok.Data;

import java.util.Date;

@Data
public class FileDO {
    private String id;

    private String path;

    private String name;

    private String suffix;

    private Integer size;

    private String use;

    private Date createdAt;

    private Date updatedAt;

    private Integer shardIndex;

    private Integer shardSize;

    private Integer shardTotal;

    private String key;

    private String vod;

}