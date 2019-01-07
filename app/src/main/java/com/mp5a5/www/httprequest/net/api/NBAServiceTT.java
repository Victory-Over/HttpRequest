package com.mp5a5.www.httprequest.net.api;

import android.util.ArrayMap;
import com.mp5a5.www.httprequest.net.entity.NBAEntity;
import com.mp5a5.www.library.use.RetrofitFactory;
import io.reactivex.Observable;

/**
 * @author ：mp5a5 on 2019/1/7 14：55
 * @describe
 * @email：wwb199055@126.com
 */
public class NBAServiceTT {

    private NBAApi mNbaApi;

    public NBAServiceTT() {
        //涉及到动态切换BaseUrl则用new Service()，不适用单例模式
        mNbaApi = RetrofitFactory.getInstance().create("http://op.juhe.cn/", NBAApi.class);
    }

    public Observable<NBAEntity> getNBAInfo(String key) {
        ArrayMap<String, Object> arrayMap = new ArrayMap<String, Object>();
        arrayMap.put("key", key);
        return mNbaApi.getNBAInfo(arrayMap);
    }
}
