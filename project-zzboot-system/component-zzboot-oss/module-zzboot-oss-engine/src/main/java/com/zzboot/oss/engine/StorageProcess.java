package com.zzboot.oss.engine;


import com.zzboot.oss.enums.EnumFileEngine;
import com.zzboot.oss.enums.EnumFileType;
import com.zzboot.oss.vo.FileVO;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * 存储器操作接口
 * @author Administrator
 */
public interface StorageProcess {

    /**
     * 获取目标文件地址
     * @param filename
     * @return
     */
    public Path getTargetPath(String filename) ;

    /**
     * 储存文件
     * @param inputStream
     * @param filename
     */
    public FileVO store(InputStream inputStream, String filename  , EnumFileType enumFileType);


    /**
     * 储存文件
     * @param bs
     * @param filename
     */
    public FileVO store(byte[] bs , String filename  ,  EnumFileType enumFileType);



    /**
     * 删除文件
     * @param filename
     */
    public boolean delete(String filename);

    /**
     * 获取文件
     * @param filename
     * @return
     */
    public Object get(String filename);






    public EnumFileEngine getEngine();

}
