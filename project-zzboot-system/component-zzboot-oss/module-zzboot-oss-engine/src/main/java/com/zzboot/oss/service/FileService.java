package com.zzboot.oss.service;


import com.zzboot.oss.vo.FileUseVO;

import java.util.List;

/**
 * @author admin
 */
public interface FileService {

    public void saveFiles(List<FileUseVO> fileUseVOs);


}
