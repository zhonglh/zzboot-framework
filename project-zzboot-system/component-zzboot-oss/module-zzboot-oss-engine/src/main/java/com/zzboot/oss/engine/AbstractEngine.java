package com.zzboot.oss.engine;

import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.oss.enums.EnumFileType;
import com.zzboot.oss.vo.FileVO;

import java.nio.file.Path;

/**
 * @author Administrator
 */
public abstract class AbstractEngine implements StorageProcess {


    @Override
    public Path getTargetPath(String filename) {
        return null;
    }


    @Override
    public FileVO store(byte[] bs , String filename  , EnumFileType enumFileType){
        throw EnumErrorMsg.code_error.toException();
    }


}
