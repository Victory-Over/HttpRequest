package com.mp5a5.www.httprequest.net.api;

import android.util.ArrayMap;
import com.mp5a5.www.httprequest.net.entity.NBAEntity;
import com.mp5a5.www.library.use.RetrofitFactory;
import io.reactivex.Observable;

/**
 * @author ：mp5a5 on 2019/1/16 10：09
 * @describe
 * @email：wwb199055@126.com
 */
public class NbaService {


    private NBAApiT nbaApiT;

    private NbaService() {
        nbaApiT = RetrofitFactory.getInstance().create(NBAApiT.class);
    }

    public static NbaService getInstance() {
        return NbaServiceHolder.S_INSTANCE;
    }

    private static class NbaServiceHolder {
        private static final NbaService S_INSTANCE = new NbaService();
    }


    public Observable<NBAEntity> getNBAInfo(String key) {
        ArrayMap<String, Object> arrayMap = new ArrayMap<String, Object>();
        arrayMap.put("key", key);
        return nbaApiT.getNBAInfo(arrayMap);
    }

}
