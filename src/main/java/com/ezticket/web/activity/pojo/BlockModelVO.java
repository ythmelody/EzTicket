package com.ezticket.web.activity.pojo;

public class BlockModelVO {
    private Integer blockno;
    private Integer modelno;
    private String blockName;
    private Integer blockType;

    public Integer getBlockType() {
        return blockType;
    }

    public void setBlockType(Integer blockType) {
        this.blockType = blockType;
    }

    public Integer getBlockno() {
        return blockno;
    }
    public void setBlockno(Integer blockno) {
        this.blockno = blockno;
    }
    public Integer getModelno() {
        return modelno;
    }
    public void setModelno(Integer modelno) {
        this.modelno = modelno;
    }
    public String getBlockName() {
        return blockName;
    }
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
