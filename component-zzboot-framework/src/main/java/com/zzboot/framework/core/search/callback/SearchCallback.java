
package com.zzboot.framework.core.search.callback;


import com.zzboot.framework.core.search.Searchable;

/**
 * @author Administrator
 */
public interface SearchCallback {

    public static final SearchCallback DEFAULT = new DefaultSearchCallback();


    /**
     * 动态拼SQL where、group by having
     *
     * @param ql
     * @param search
     */
    public void prepareQL(StringBuilder ql, Searchable search);


}
