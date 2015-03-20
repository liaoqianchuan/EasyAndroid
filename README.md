EasyAndroid
==============================
大纲
------------------------------
* EasyLog
* EasyDb
* EasyFile
* EasyHttp
* EasyReflection

###EasyLog
用法很简单，跟android得Logcat用法一样，但是EasyLog会很方便的帮你打出一些额外的信息。
  
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyLog.d("hello easy android");
    }
    
运行程序，打印出下面的log信息:

03-20 17:20:18.040: E/com.amida.easyandroid.test.MainActivity(8844): [onCreate:40]hello easy android

EasyLog中以当前的类名为tag，在message中打印出了额外的信息，包括所在函数，代码行数。


  
