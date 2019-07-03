package com.zzboot.system.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumSymbol;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.framework.core.ui.easyui.EasyUiTree;
import com.zzboot.framework.core.ui.easyui.EasyUiTreeUtil;
import com.zzboot.system.bo.*;
import com.zzboot.system.enums.EnumRoleStatus;
import com.zzboot.system.enums.EnumRoleType;
import com.zzboot.system.enums.EnumSystemDictType;
import com.zzboot.system.query.impl.TsRoleQueryWebImpl;
import com.zzboot.system.service.*;
import com.zzboot.util.base.data.MyBeanUtils;
import com.zzboot.util.base.java.IdUtils;
import com.zzboot.util.poi.ExcelDictHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色 控制层
* @author Administrator
* @date 2018-9-6 23:56:31
 */
@RequestMapping("/system/role")
@Controller
public class TsRoleController extends ZzGroupDefaultController<TsRoleGroupBO, TsRoleGroupBO , String , TsRoleQueryWebImpl,TsRoleQueryWebImpl> {



	@Autowired
	private TsMenuService menuService;

    @Autowired
	private TsRolePermitService rolePermitService;

	@Autowired
	private VsMenuPermitService menuPermitService;

    @Autowired
    private TsDepService depService;


    @Autowired
    private TsDictService tsDictService;


	private static List<EasyUiTree> allPermit = null;


    /**
     * 收集信息 ，补齐 TsRoleGroupBO 的内容
     * 将分配给角色的许可ID放入到 对象中
     * @param m
     * @param model
     * @param sessionUserVO
     * @param request
     * @param response
     */
    @Override
    protected void gatherCreateInformation(TsRoleGroupBO m, ModelMap model , ILoginUserEntity<String> sessionUserVO, HttpServletRequest request, HttpServletResponse response){
        String permitIds = request.getParameter("permitIds");
        if(StringUtils.isNotEmpty(permitIds) && (  m.getRolePermitList() == null || m.getRolePermitList().isEmpty()  ) ){
            String[] permitIdArray = permitIds.split( EnumSymbol.COMMA.getCode() );
            List<TsRolePermitBO> rolePermitBOList = new ArrayList<TsRolePermitBO>();
            for(String permitId : permitIdArray){
                TsRolePermitBO rolePermitBO = new TsRolePermitBO();
                rolePermitBO.setPermitId(permitId);
                rolePermitBOList.add(rolePermitBO);
            }
            m.setRolePermitList(rolePermitBOList);
        }
    }


    /**
     * 收集信息 ，补齐 TsRoleGroupBO 的内容
     * 将分配给角色的许可ID放入到 对象中
     * @param m
     * @param model
     * @param sessionUserVO
     * @param request
     * @param response
     */
    @Override
    protected void gatherUpdateInformation(TsRoleGroupBO m, ModelMap model , ILoginUserEntity<String> sessionUserVO, HttpServletRequest request, HttpServletResponse response){

        String permitIds = request.getParameter("permitIds");
        if(StringUtils.isNotEmpty(permitIds) && (  m.getRolePermitList() == null || m.getRolePermitList().isEmpty()  ) ){
            String[] permitIdArray = permitIds.split( EnumSymbol.COMMA.getCode() );
            List<TsRolePermitBO> rolePermitBOList = new ArrayList<TsRolePermitBO>();
            for(String permitId : permitIdArray){
                TsRolePermitBO rolePermitBO = new TsRolePermitBO();
                rolePermitBO.setPermitId(permitId);
                rolePermitBO.setRoleId(m.getId());
                rolePermitBO.setId(IdUtils.getId());
                rolePermitBOList.add(rolePermitBO);
            }
            m.setRolePermitList(rolePermitBOList);
        }
    }

    /**
     * 保存前设置一些 业务定制的值
     * @param m
     */
    @Override
    protected void setCustomInfoByInsert(TsRoleGroupBO m , ILoginUserEntity<String> sessionUser){

        m.setRoleStatus(EnumRoleStatus.normal.getVal());
        m.setRoleStatusName(EnumRoleStatus.normal.getLabel());
        m.setRoleType(EnumRoleType.USER_ROLE.getVal());
        m.setRoleTypeName(EnumRoleType.USER_ROLE.getLabel());

        if(StringUtils.isEmpty(m.getOrganId())){
            if(StringUtils.isEmpty(m.getDepId())){
                m.setOrganId((String)sessionUser.getOrganId());
            }else {
                TsDepBO depBO = depService.getById(m.getDepId());
                if(depBO != null){
                    m.setOrganId(depBO.getOrganId());
                }
            }
        }

        if(m.getRolePermitList() != null && !m.getRolePermitList().isEmpty()){
            for(TsRolePermitBO rolePermitBO : m.getRolePermitList()){
                rolePermitBO.setId(IdUtils.getId());
                rolePermitBO.setRoleId(m.getId());
            }
        }
    }



    @Override
    protected void setCommonData(TsRoleGroupBO tbFundBO , ModelMap model) {
        Map<String , List<TsDictBO>> dictMap = tsDictService.allDicts(EnumSystemDictType.ROLE_TYPE.getVal() , EnumSystemDictType.ROLE_STATUS.getVal());
        for(Map.Entry<String , List<TsDictBO>> dictObj : dictMap.entrySet()){
            model.put(dictObj.getKey()+"_dicts", dictObj.getValue());
        }
    }



    @Override
    public void setDictNames() {
        Map<String , List<String>> dictMap = tsDictService.allDictNames(EnumSystemDictType.ROLE_TYPE.getVal() , EnumSystemDictType.ROLE_STATUS.getVal());
        ExcelDictHolder.setDictMap(dictMap);
    }




    /**
     * 拿到所有的许可
     * @return
     */
	private List<EasyUiTree> getAllPermitTree(){
	    if(allPermit == null) {
            synchronized (this) {
                if(allPermit == null) {


                    List<EasyUiTree> trees = new ArrayList<EasyUiTree>();
                    List<TsMenuBO> menus = menuService.list();
                    List<VsMenuPermitBO> menuPermits = menuPermitService.list();

                    List<TsMenuBO> temps = new ArrayList<>();
                    for (TsMenuBO menu : menus) {
                        trees.add(menu.toEasyUiTree());
                    }
                    for (VsMenuPermitBO menuPermit : menuPermits) {
                        trees.add(menuPermit.toEasyUiTree());
                    }

                    allPermit = trees;

                }
            }
        }


        try {
            return (List<EasyUiTree>) MyBeanUtils.deepCopy(allPermit);
        }catch(Exception e){
            throw new BizException(0,"出现错误，请重试");
        }

    }



    @RequestMapping(value = "/permitSelectedTree/{roleId}" , method={ RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public List<EasyUiTree> permitSelectedTree(@PathVariable("roleId") String roleId){


        List<EasyUiTree> easyUiTrees = getAllPermitTree();
        List<String> rolePermitIds = null;

        if(StringUtils.isNotEmpty(roleId) && !"0".equals(roleId)){
            QueryWrapper<TsRolePermitBO> queryWrapper = new QueryWrapper<TsRolePermitBO>();
            queryWrapper.lambda().eq(TsRolePermitBO::getRoleId,roleId);
            List<TsRolePermitBO> rolePermitBOs = rolePermitService.list(queryWrapper);
            if(rolePermitBOs != null && !rolePermitBOs.isEmpty()){

                rolePermitIds = new ArrayList<String>();
                for(TsRolePermitBO rolePermitBO : rolePermitBOs){
                    rolePermitIds.add(rolePermitBO.getPermitId());
                }
            }
        }

        List<EasyUiTree> rootTree = EasyUiTreeUtil.buildToOnlySelectedTree(easyUiTrees, "所有许可" , rolePermitIds);


        return rootTree;

    }

	@RequestMapping(value = "/permitTree/{roleId}" , method={ RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public List<EasyUiTree> permitTree(@PathVariable("roleId") String roleId){

        List<EasyUiTree> easyUiTrees = getAllPermitTree();
        List<String> rolePermitIds = null;

        if(StringUtils.isNotEmpty(roleId) && !"0".equals(roleId)){
            QueryWrapper<TsRolePermitBO> queryWrapper = new QueryWrapper<TsRolePermitBO>();
            queryWrapper.lambda().eq(TsRolePermitBO::getRoleId,roleId);
            List<TsRolePermitBO> rolePermitBOs = rolePermitService.list(queryWrapper);
            if(rolePermitBOs != null && !rolePermitBOs.isEmpty()){

                rolePermitIds = new ArrayList<String>();
                for(TsRolePermitBO rolePermitBO : rolePermitBOs){
                    rolePermitIds.add(rolePermitBO.getPermitId());
                }
            }
        }

        List<EasyUiTree> rootTree = EasyUiTreeUtil.buildToTree(easyUiTrees, "所有许可" , rolePermitIds);


        return rootTree;

	}




}
