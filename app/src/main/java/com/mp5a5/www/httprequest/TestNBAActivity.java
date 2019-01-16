package com.mp5a5.www.httprequest;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.mp5a5.www.httprequest.net.api.NbaService;
import com.mp5a5.www.httprequest.net.entity.NBAEntity;
import com.mp5a5.www.library.use.BaseObserver;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ：mp5a5 on 2019/1/7 14：03
 * @describe
 * @email：wwb199055@126.com
 */
public class TestNBAActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnNBA).setOnClickListener(v -> {
            NbaService.getInstance()
                    .getNBAInfo("6949e822e6844ae6453fca0cf83379d3")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.bindToLifecycle())
                    .subscribe(new BaseObserver<NBAEntity>(){

                        @Override
                        public void onSuccess(NBAEntity response) {
                            Toast.makeText(TestNBAActivity.this, response.result.title, Toast.LENGTH_SHORT).show();
                        }

                    });


        });


    }

}

