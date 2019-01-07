# HttpRequest

基于Retrofit2+RxJava+OkHttp3+RxLifecycle3的网络请求框架

#### 1、添加依赖和配置

* 工程添加依赖仓库，Add the JitPack repository to your build file

```Java
allprojects {
   repositories {
   		...
   	    maven { url 'https://jitpack.io' }
   }
}
```

```Java
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```

```Java
dependencies {

       implementation 'com.github.Mp5A5:HttpRequest:1.0.2'
}
```

#### 2、使用步骤

###### 1.在Application类中进行初始化操作

```Java
@Override
    public void onCreate() {
        super.onCreate();

        String baseUrl = "http://op.juhe.cn/";
        ArrayMap<String, String> headMap = new ArrayMap<String, String>();
        headMap.put("key1", "value1");
        headMap.put("key2", "value2");
        headMap.put("key3", "value3");

        SslSocketConfigure sslSocketConfigure = new SslSocketConfigure.Builder()
                .setVerifyType(2)//单向双向验证 1单向  2 双向
                .setClientPriKey("client.bks")//客户端keystore名称
                .setTrustPubKey("truststore.bks")//受信任密钥库keystore名称
                .setClientBKSPassword("123456")//客户端密码
                .setTruststoreBKSPassword("123456")//受信任密钥库密码
                .setKeystoreType("BKS")//客户端密钥类型
                .setProtocolType("TLS")//协议类型
                .setCertificateType("X.509")//证书类型
                .build();


        ApiConfig build = new ApiConfig.Builder()
                .setBaseUrl(baseUrl)//BaseUrl，这个地方加入后项目中默认使用该url
                .setInvalidateToken(0)//Token失效码
                .setSucceedCode(200)//成功返回码
                .setFilter("com.mp5a5.quit.broadcastFilter")//失效广播Filter设置
                //.setDefaultTimeout(2000)//响应时间，可以不设置，默认为2000毫秒
                //.setHeads(headMap)//动态添加的header，也可以在其他地方通过ApiConfig.setHeads()设置
                //.setOpenHttps(true)//开启HTTPS验证
                //.setSslSocketConfigure(sslSocketConfigure)//HTTPS认证配置
                .build();
        /*
         *     Token失效后发送动态广播的Filter，配合BaseObserver中的标识进行接收使用
         *     public static final String TOKEN_INVALID_TAG = "token_invalid"; ------------>>>>>>>>>>对应name
         *     public static final String QUIT_APP = "quit_app"; ------------>>>>>>>>>>对应value
         *
         *
         *     oncreate()方法中初始化
         *     private void initReceiver() {
         *         mQuitAppReceiver = new QuitAppReceiver();
         *         IntentFilter filter = new IntentFilter();
         *         filter.addAction(ApiConfig.getQuitBroadcastReceiverFilter());
         *         registerReceiver(mQuitAppReceiver, filter);
         *     }
         *
         *
         *     private class QuitAppReceiver extends BroadcastReceiver {
         *
         *         @Override
         *         public void onReceive(Context context, Intent intent) {
         *             if (ApiConfig.getQuitBroadcastReceiverFilter().equals(intent.getAction())) {
         *
         *                 String msg = intent.getStringExtra(BaseObserver.TOKEN_INVALID_TAG);
         *                 if (!TextUtils.isEmpty(msg)) {
         *                     Toast.makeText(TestActivity.this, msg, Toast.LENGTH_SHORT).show();
         *                 }
         *             }
         *         }
         *     }
         *
         */

        build.init(this);
    }

```

```Java
kotlin中使用
override fun onCreate() {
        super.onCreate()

        val baseUrl = "http://op.juhe.cn/"
        val headMap = ArrayMap<String, String>()
        headMap["key1"] = "value1"
        headMap["key2"] = "value2"
        headMap["key3"] = "value3"

        val sslSocketConfigure = SslSocketConfigure.Builder()
            .setVerifyType(2)//单向双向验证
            .setClientPriKey("client.bks")//客户端keystore名称
            .setTrustPubKey("truststore.bks")//受信任密钥库keystore名称
            .setClientBKSPassword("123456")//客户端密码
            .setTruststoreBKSPassword("123456")//受信任密钥库密码
            .setKeystoreType("BKS")//客户端密钥类型
            .setProtocolType("TLS")//协议类型
            .setCertificateType("X.509")//证书类型
            .build();


        val build = ApiConfig.Builder()
            .setBaseUrl(baseUrl)//BaseUrl，这个地方加入后项目中默认使用该url
            .setInvalidateToken(0)//Token失效码
            .setSucceedCode(200)//成功返回码
            /*
             *    Token失效后发送动态广播，配合BaseObserver中的标识进行接收使用
             *    public static final String TOKEN_INVALID_TAG = "token_invalid";
             *    public static final String QUIT_APP = "quit_app";
             *    注册Token失效，退出登录的动态广播
             *    private inner class QuitAppReceiver : BroadcastReceiver() {
             *          override fun onReceive(context: Context?, intent: Intent?) {
             *               if (ApiConfig.getQuitBroadcastReceiverFilter() == intent?.action) {
             *
             *                   val msg = intent?.getStringExtra(BaseObserver.TOKEN_INVALID_TAG)
             *
             *                   if (!TextUtils.isEmpty(msg)) {
             *                       toast("$msg")
             *                       //Toast.makeText(this@MainActivity,msg,Toast.LENGTH_SHORT).show()
             *                  }
             *               }
             *           }
             *       }
             *
             *
             *
             *
             *   onCreate中
             *   private fun initReceiver() {
             *   val filter = IntentFilter()
             *   filter.addAction(ApiConfig.getQuitBroadcastReceiverFilter())
             *   registerReceiver(quitAppReceiver, filter)
             *   }
             */
            .setFilter("com.mp5a5.quit.broadcastFilter")////失效广播Filter设置
            //.setDefaultTimeout(2000)//响应时间，可以不设置，默认为2000毫秒
            //.setHeads(headMap)//动态添加的header，也可以在其他地方通过ApiConfig.setHeads()设置
            //.setOpenHttps(true)//开启HTTPS验证
            //.setSslSocketConfigure(sslSocketConfigure)//HTTPS认证配置
            .build()
        build.init(this)

    }

   ```
###### 2.定义接口

```Java
public interface UploadApi {

    @Multipart
    @POST("ues/app/upload/pictures")
    Observable<UploadEntity> postUpload(@Part List<MultipartBody.Part> partList);
}
```

```Java
kotlin中使用
interface NBAApi {

    @GET("onebox/basketball/nba")
    fun getNBAInfo(@QueryMap map: ArrayMap<String, @JvmSuppressWildcards Any>): Observable<NBAEntity>
}
```
###### 3.创建实例
```Java
单例模式创建Service，推荐使用这种
public class UploadService {

    private final UploadApi mUploadApi;

    private UploadService() {
        //推荐使用这种，因为BaseUrl已经初始化了
        //mUploadApi = RetrofitFactory.getInstance().create(UploadApi.class);
        String url = "http://fnw-api-nginx-fnw-test.topaas.enncloud.cn/";
        mUploadApi = RetrofitFactory.getInstance().create(url,UploadApi.class);
    }

    public static UploadService getInstance() {
        return UploadServiceHolder.S_INSTANCE;
    }

    private static class UploadServiceHolder {
        private static final UploadService S_INSTANCE = new UploadService();
    }

    public Observable<UploadEntity> uploadPic(List<MultipartBody.Part> picList) {
        return mUploadApi.postUpload(picList);
    }

}
```

```Java
使用new Service创建Service，这中用来做动态切换BaseUrl测试等
public class NBAServiceTT {

    private NBAApi mNbaApi;

    public NBAServiceTT() {
        //涉及到动态切换BaseUrl则用new Service()，不使用单例模式
        mNbaApi = RetrofitFactory.getInstance().create("http://op.juhe.cn/", NBAApi.class);
    }

    public Observable<NBAEntity> getNBAInfo(String key) {
        ArrayMap<String, Object> arrayMap = new ArrayMap<String, Object>();
        arrayMap.put("key", key);
        return mNbaApi.getNBAInfo(arrayMap);
    }
}
```

```Java
kotlin中使用
object NBAService {

    private val mNBAApi = RetrofitFactory.getInstance().create(NBAApi::class.java)

    fun getNBAInfo(key: String): Observable<NBAEntity> {
        val arrayMap = ArrayMap<String, Any>()
        arrayMap["key"] = key
        return mNBAApi.getNBAInfo(arrayMap)
    }
}

或者

//这中方式用于app中存在其他BaseUrl的请求----->一般不会用到，推荐使用第一种，因为application中已经注册
object NBAServiceT {

    private val mNBAApi = RetrofitFactory.getInstance().create("http://op.juhe.cn/",NBAApi::class.java)

    fun getNBAInfo(key: String): Observable<NBAEntity> {
        val arrayMap = ArrayMap<String, Any>()
        arrayMap["key"] = key
        return mNBAApi.getNBAInfo(arrayMap)
    }
}

```

###### 4.设置接收参数
```Java
实体类必须继承BaseResponseEntity，如果公司返回的参数不叫code，则使用@SerializedName("value")起别名的方式，写个别名
public class NBAEntity extends BaseResponseEntity {


    @SerializedName("error_code")
    public int code;
    public String reason;
    public ResultBean result;


    public static class ResultBean {


        public String title;
        public StatuslistBean statuslist;
        public List<ListBean> list;
        public List<TeammatchBean> teammatch;


        public static class StatuslistBean {


            public String st0;
            public String st1;
            public String st2;


        }

        public static class ListBean {


            public String title;
            public List<TrBean> tr;
            public List<BottomlinkBean> bottomlink;
            public List<LiveBean> live;
            public List<LivelinkBean> livelink;


            public static class TrBean {


                public String link1text;
                public String link1url;
                public String link2text;
                public String link2url;
                public String player1;
                public String player1logo;
                public String player1logobig;
                public String player1url;
                public String player2;
                public String player2logo;
                public String player2logobig;
                public String player2url;
                public String score;
                public int status;
                public String time;


            }

            public static class BottomlinkBean {


                public String text;
                public String url;

            }

            public static class LiveBean {


                public String date;
                public String liveurl;
                public String player1;
                public String player1info;
                public String player1location;
                public String player1logo;
                public String player1logobig;
                public String player1url;
                public String player2;
                public String player2info;
                public String player2location;
                public String player2logo;
                public String player2logobig;
                public String player2url;
                public String score;
                public int status;
                public String title;


            }

            public static class LivelinkBean {

                public String text;
                public String url;
                public String videoicon;


            }
        }

        public static class TeammatchBean {

            public String name;
            public String url;

        }
    }
}

```
###### 5.发送请求,接收参数

```Java

public class TestActivity extends RxAppCompatActivity {

    private QuitAppReceiver mQuitAppReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initReceiver();


        new NBAServiceTT()
                .getNBAInfo("6949e822e6844ae6453fca0cf83379d3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseObserver<NBAEntity>(this, true) {
                    @Override
                    public void onSuccess(NBAEntity response) {
                        Toast.makeText(TestActivity.this, response.result.title, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void initReceiver() {
        mQuitAppReceiver = new QuitAppReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ApiConfig.getQuitBroadcastReceiverFilter());
        registerReceiver(mQuitAppReceiver, filter);
    }


    private class QuitAppReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ApiConfig.getQuitBroadcastReceiverFilter().equals(intent.getAction())) {

                String msg = intent.getStringExtra(BaseObserver.TOKEN_INVALID_TAG);
                if (!TextUtils.isEmpty(msg)) {
                    Toast.makeText(TestActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

```

```Java
kotlin中使用
btnNBA.setOnClickListener {
            NBAService
                .getNBAInfo("6949e822e6844ae6453fca0cf83379d3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(object : BaseObserver<NBAEntity>(this,true) {
                    override fun onSuccess(response: NBAEntity?) {
                        toast(response?.result?.title!!)
                        //设置成功后的Token
                        //ApiConfig.setToken(response?.token)
                    }

                    //失败和错误回调，默认可以不重写，父类已经做了Toast处理，如果需要展示错误页面等，可以重写
                    <!--override fun onFailing(response: NBAEntity?) {
                        super.onFailing(response)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                    }-->

                })
        }

tvTest.setOnClickListener {
    NBAServiceT
        .getNBAInfo("6949e822e6844ae6453fca0cf83379d3")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(this.bindToLifecycle())
        .subscribe(object : BaseObserver<NBAEntity>() {
            override fun onSuccess(response: NBAEntity?) {
                toast(response?.result?.title!!)
            }

        })
}

```

#### 3、效果展示

![show.gif](img/show.gif)

