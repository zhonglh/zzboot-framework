package com.zzboot.framework.shiro;

import com.zzboot.framework.core.db.entity.IOnlineUserEnitty;
import com.zzboot.framework.shiro.session.OnlineSession;

import java.util.Date;
import java.util.List;

/**
 * @author zhonglh
 */
public interface IOnlineUserService {

    public void saveOnline(OnlineSession onlineSession) ;
    public void online(IOnlineUserEnitty  onlineUser) ;


    public void offline(String sid)  ;


    public IOnlineUserEnitty selectOnlineById(String sessionId);


    public void deleteSession(String sessionId) ;

    public void batchDeleteOnlineUser(List<String> sessionIds);


    public List<IOnlineUserEnitty> selectOnlineUserByExpired(Date expiredDate);

}
