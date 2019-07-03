package com.zzboot.oss.vo;


import com.zzboot.system.bo.TsFileBO;
import com.zzboot.system.bo.TsFileUseBO;

/**
 * @author admin
 */
public class FileUseVO {
    private TsFileBO tsFileBO ;
    private TsFileUseBO tsFileUseBO;

    public FileUseVO() {
    }

    public FileUseVO(TsFileBO tsFileBO, TsFileUseBO tsFileUseBO) {
        this.tsFileBO = tsFileBO;
        this.tsFileUseBO = tsFileUseBO;
    }

    public TsFileBO getTsFileBO() {
        return tsFileBO;
    }

    public void setTsFileBO(TsFileBO tsFileBO) {
        this.tsFileBO = tsFileBO;
    }

    public TsFileUseBO getTsFileUseBO() {
        return tsFileUseBO;
    }

    public void setTsFileUseBO(TsFileUseBO tsFileUseBO) {
        this.tsFileUseBO = tsFileUseBO;
    }
}
