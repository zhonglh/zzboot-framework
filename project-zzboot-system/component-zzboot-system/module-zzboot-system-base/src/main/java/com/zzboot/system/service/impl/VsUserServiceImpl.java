package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.enums.EnumDictType;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.VsUserBO;
import com.zzboot.system.dao.VsUserDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.VsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:56:22
 */
@Service
public class VsUserServiceImpl extends SystemBaseServiceImpl<VsUserBO,String> implements VsUserService {



    @Autowired
    private TsDictService tsDictService;





    @Autowired
    private VsUserDAO vsUserDAO ;



    @Override
    public BaseDAO getDAO() {
        return vsUserDAO ;
    }



    @Override
    public VsUserBO processResult(VsUserBO vsUserBO) {

        try {
            if(StringUtils.isEmpty(vsUserBO.getUserStatusName()) && StringUtils.isNotEmpty(vsUserBO.getUserStatus()) ) {
                String dictName = tsDictService.getDictName(vsUserBO.getUserStatus(),EnumDictType.USER_STATUS.getVal());
                vsUserBO.setUserStatusName(dictName);
            }
        }catch(Exception e){

        }
        try {
            if(StringUtils.isEmpty(vsUserBO.getSystemAdminName()) && StringUtils.isNotEmpty(vsUserBO.getSystemAdmin()) ) {
                String dictName = tsDictService.getDictName(vsUserBO.getSystemAdmin(),EnumDictType.YES_NO.getVal());
                vsUserBO.setSystemAdminName(dictName);
            }
        }catch(Exception e){

        }

        return vsUserBO;

    }

    @Override
    public void specialHandler(VsUserBO t){
        t.setLoginPassword(null);
        t.setSalt(null);
    }


    @Override
    public void specialHandler(List<VsUserBO> ts){
        if(ts != null && !ts.isEmpty()) {
            for (VsUserBO user : ts) {
                specialHandler(user);
            }
        }
    }

    @Override
    public List<VsUserBO> processResult(List<VsUserBO> vsUserBOs) {
        if(vsUserBOs == null || vsUserBOs.isEmpty()){
            return vsUserBOs;
        }


        String[] dictTypes = new String[]{EnumDictType.USER_STATUS.getVal(),EnumDictType.YES_NO.getVal()};
        Map<String , TsDictBO> dictMap = tsDictService.allDict(dictTypes);

        vsUserBOs.forEach(vsUserBO -> {
            if(StringUtils.isEmpty(vsUserBO.getUserStatusName()) && StringUtils.isNotEmpty(vsUserBO.getUserStatus()) ) {
                TsDictBO dict = dictMap.get(EnumDictType.USER_STATUS.getVal() + vsUserBO.getUserStatus());
                if(dict != null) {
                    vsUserBO.setUserStatusName(dict.getDictName());
                }
            }
            if(StringUtils.isEmpty(vsUserBO.getSystemAdminName()) && StringUtils.isNotEmpty(vsUserBO.getSystemAdmin()) ) {
                TsDictBO dict = dictMap.get(EnumDictType.YES_NO.getVal() + vsUserBO.getSystemAdmin());
                if(dict != null) {
                    vsUserBO.setSystemAdminName(dict.getDictName());
                }
            }
        });



        return vsUserBOs;
    }




    @Override
    public void isExist(VsUserBO vsUserBO) {
    }


}