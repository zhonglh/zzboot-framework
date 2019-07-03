package com.zzboot.system.controller;


import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.mybatis.query.Query;

import java.io.Serializable;


/**
 * zz 专用 base controller
 * 其它项目可以按照需求， 重新设计一个base controller
 *
 *
 * @param <M>
 * @param <PK>
 * @param <Q>
 */
public abstract class ZzDefaultSimpleController<M extends BaseEntity<PK>, PK extends Serializable , Q extends Query>
            extends ZzDefaultController<M  , M, PK , Q , Q> {


}
