package com.zzboot.system.controller;


import com.zzboot.system.bo.TsDictTypeBO;
import com.zzboot.system.query.impl.TsDictTypeQueryWebImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 字典类型 控制层
* @author Administrator
* @date 2018-9-6 23:56:30
 */
@RequestMapping("/system/dicttype")
@Controller
public class TsDictTypeController extends ZzDefaultSimpleController<TsDictTypeBO, String , TsDictTypeQueryWebImpl> {







}
