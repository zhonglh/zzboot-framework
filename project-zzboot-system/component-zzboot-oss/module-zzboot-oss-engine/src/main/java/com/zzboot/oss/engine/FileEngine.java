package com.zzboot.oss.engine;

import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.oss.config.FileSystemConfig;
import com.zzboot.oss.enums.EnumFileEngine;
import com.zzboot.oss.enums.EnumFileType;
import com.zzboot.oss.vo.FileVO;
import com.zzboot.util.web.IpUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 文件系统实现 存储
 * Java 8   NIO
 * @author Administrator
 */

@Component("FILESYSTEM")
@ConditionalOnProperty(prefix="zzboot" ,  name = "ossEngine" , havingValue = "FILESYSTEM" , matchIfMissing = true)
public  class FileEngine extends AbstractEngine implements StorageProcess {

    @Autowired
    private FileSystemConfig config;

    private  Path rootLocation = null;

    @PostConstruct
    public void init(){
        rootLocation = Paths.get("/");
    }


    @Override
    public FileVO store(byte[] bs , String filename  , EnumFileType enumFileType){
        try {
            Path target = getTargetPath(filename);


            if(enumFileType == EnumFileType.ImageType){

                OutputStream os = null;
                try {
                    os = new FileOutputStream(target.toFile());
                    os.write(bs);
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.getLocalizedMessage();
                        }
                    }
                }


            }else {

                throw EnumErrorMsg.code_error.toException();
            }

            FileVO fileVO = new FileVO();
            fileVO.setFileBasePath(rootLocation.toAbsolutePath().toString());
            fileVO.setFilePath(target.toAbsolutePath().toString());
            fileVO.setFileName(target.toFile().getName());
            fileVO.setAccessUrl("/oss/file/view/");
            fileVO.setAccessUrlPrefix("");
            fileVO.setFileHost(IpUtil.getIp());
            return fileVO;
        }
        catch (Exception e) {
            throw new RuntimeException ("Failed to store file " + filename, e);
        }
    }



    @Override
    public FileVO store(InputStream inputStream, String filename , EnumFileType enumFileType) {
        try {
            Path target = getTargetPath(filename);


            if(enumFileType == EnumFileType.ImageType){
                throw EnumErrorMsg.code_error.toException();
            }else {
                FileUtils.copyInputStreamToFile(inputStream, target.toFile());
            }

            FileVO fileVO = new FileVO();
            fileVO.setFileBasePath(rootLocation.toAbsolutePath().toString());
            fileVO.setFilePath(target.toAbsolutePath().toString());
            fileVO.setFileName(target.toFile().getName());
            fileVO.setAccessUrl("/oss/file/view/");
            fileVO.setAccessUrlPrefix("");
            fileVO.setFileHost(IpUtil.getIp());
            return fileVO;
        }
        catch (IOException e) {
            throw new RuntimeException ("Failed to store file " + filename, e);
        }
    }

    @Override
    public Path getTargetPath(String filename) {
        Path target = rootLocation.resolve(config.getRoot() + filename) ;
        if(!target.toFile().getParentFile().exists()){
            target.toFile().getParentFile().mkdirs();
        }
        return target;
    }


    @Override
    public boolean delete(String filename) {
        Path file = load(filename);
        try {
            Files.delete(file);
            return true;
        } catch (IOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Object get(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource.getFile();
            }else {
                return null;
            }
        }catch(MalformedURLException murlException){
            throw new BizException(murlException);
        }catch(Exception e){
            throw new BizException(e);
        }
    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }




    @Override
    public EnumFileEngine getEngine() {
        return EnumFileEngine.FILESYSTEM;
    }
}
