package com.zzboot.system.controller;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.mybatis.query.Query;

import java.io.Serializable;

/**
 * @author zhonglh
 */
public abstract class ZzGroupDefaultSimpleController<M extends BaseEntity<PK>, PK extends Serializable, Q extends Query>
        extends ZzGroupDefaultController<M  , M, PK , Q , Q> {
}
