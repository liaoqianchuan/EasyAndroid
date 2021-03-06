EasyAndroid

**该开源库参考了ActiveAndroid，retrofit，并使用了sqlcipher-for-android和android-async-http，在这些库的思想上做了一些适合自己项目的扩展。本着方便自己开发Android程序的目的诞生了这个库。目前该库还在不断完善中**
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyLog.d("hello easy android");
    }
    
运行程序，打印出下面的log信息:

    03-20 17:20:18.040: E/com.amida.easyandroid.test.MainActivity(8844): [onCreate:40]hello easy android

EasyLog中以当前的类名为tag，在message中打印出了额外的信息，包括所在函数，代码行数。
EasyLog在我们调试程序的时候，还可以打印长log，通常我们在打印很长的log的时候，后面超出的部分就不现实了, 使用EasyLog，会方便的为我们分几次打印出整个字符串，如下:
    
    StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 4000; i ++) {
            longText.append(i);
        }
        EasyLog.d(longText.toString());
    
    03-24 11:12:15.092: E/com.amida.easyandroid.test.MainActivity(15309): [onCreate:44]012345678910111213141516171819202122232425262728293031323334353637383940414243444546474849505152535455565758596061626364656667686970717273747576777879808182838485868788899091929394959697989910010110210310410510610710810911011111211311411511611711811912012112212312412512612712812913013113213313413513613713813914014114214314414514614714814915015115215315415515615715815916016116216316416516616716816917017117217317417517617717817918018118218318418518618718818919019119219319419519619719819920020120220320420520620720820921021121221321421521621721821922022122222322422522622722822923023123223323423523623723823924024124224324424524624724824925025125225325425525625725825926026126226326426526626726826927027127227327427527627727827928028128228328428528628728828929029129229329429529629729829930030130230330430530630730830931031131231331431531631731831932032132232332432532632732832933033133233333433533633733833934034134234334434534634734834935035135235335435535635735835936036136236336436536636736836937037137237337437537637737837938038138238338438538638738838939039139239339439539639739839940040140240340440540640740840941041141241341441541641741841942042142242342442542642742842943043143243343443543643743843944044144244344444544644744844945045145245345445545645745845946046146246346446546646746846947047147247347447547647747847948048148248348448548648748848949049149249349449549649749849950050150250350450550650750850951051151251351451551651751851952052152252352452552652752852953053153253353453553
    03-24 11:12:15.092: E/com.amida.easyandroid.test.MainActivity(15309): [onCreate:44]653753853954054154254354454554654754854955055155255355455555655755855956056156256356456556656756856957057157257357457557657757857958058158258358458558658758858959059159259359459559659759859960060160260360460560660760860961061161261361461561661761861962062162262362462562662762862963063163263363463563663763863964064164264364464564664764864965065165265365465565665765865966066166266366466566666766866967067167267367467567667767867968068168268368468568668768868969069169269369469569669769869970070170270370470570670770870971071171271371471571671771871972072172272372472572672772872973073173273373473573673773873974074174274374474574674774874975075175275375475575675775875976076176276376476576676776876977077177277377477577677777877978078178278378478578678778878979079179279379479579679779879980080180280380480580680780880981081181281381481581681781881982082182282382482582682782882983083183283383483583683783883984084184284384484584684784884985085185285385485585685785885986086186286386486586686786886987087187287387487587687787887988088188288388488588688788888989089189289389489589689789889990090190290390490590690790890991091191291391491591691791891992092192292392492592692792892993093193293393493593693793893994094194294394494594694794894995095195295395495595695795895996096196296396496596696796896997097197297397497597697797897998098198298398498598698798898999099199299399499599699799899910001001100210031004100510061007100810091010101110121013101410151016101710181019102010211022102310241025102610
    
    
###EasyDb
1. 加密的数据库，更安全。
2. 不需要用户敲入大量的databaseHelper的代码。
3. 简单的数据库操作。

#####配置

1. 将icudt46l.zip拷贝到项目assets目录下。
2. 在AndroidManifest.xml文件的Application内加入
        
        <meta-data
            android:name="DB_NAME"
            android:value="sampledb" >
        </meta-data>
        <meta-data
            android:name="DB_VERSION"
            android:value="1" >
        </meta-data>
        <meta-data
            android:name="DB_TABLSE_PACKAGE"
            android:value="com.example.textviewfinder.dbbean" >
        </meta-data>
        
    设置数据库名，版本，数据库表结构所在的包。
3. 新建自己的Application类
        
        public class SampleApp extends Application{
        
        @Override
        public void onCreate() {
        super.onCreate();
                
        EasyDb.getInstance().init(this, "secretkey"); 
        }
        
    初始化数据库。

4. 在com.amida.easydb.DbTable下面新建你需要的数据库表
        
        public class Team extends DbTable {
        @Column(name = "teamName")
        private String teamName;
        @Column(name = "teamLeader")
        private String teamLeader;
        
        public String getTeamName() {
            return teamName;
        }
            
        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }
            
        public String getTeamLeader() {
            return teamLeader;
        }
        
        public void setTeamLeader(String teamLeader) {
            this.teamLeader = teamLeader;
        }
        
        }
        
    定义表结构，表名就是类名，列名由Column标签指定。

#####保存数据
    
    Team team = new Team();
    team.setTeamLeader("liaoqianchuan");
    team.setTeamName("OneTeam");
    team.save();
    
#####查询数据
    
    ArrayList<Team> teams = new Select().from(Team.class).orderBy("teamLeader").execute();
    
#####删除数据
    
    team.delete();
    
    new Delete().from(Team.class).where("teamName='liaoqianchuan'").execute();
    
#####更新数据
    
    ArrayList<Team> teams = new Select().from(Team.class).orderBy("teamLeader").execute();
    
    for (Team tmpTeam : teams) {
        tmpTeam.setTeamLeader("Gebi LaoWang");
        tmpTeam.save();
    }
    
###EasyHttp
* 用户只需定义Service的接口，不需要写Http的实现
* 可以很方便的扩展其他业务逻辑。
* 支持假数据，比如只想本地调试，而不想和服务器联调，可以很方便的设置假数据供本地测试。

#####定义接口
如下所示，定义了两个接口，第一个是取IP信息的接口，第二个是下载图片的接口。
EasyHttp标签：
1.  url - 定义该接口指向的url。
2.  dummyData - 定义该接口返回的假数据。
3.  apiType - 有下载文件和一般的获取数据类型
    
    
    public interface TestService extends IService {
    @EasyHttp(
            url = Constants.API_IPINFO,
            dummyData = DummyData.DUMMY_IPINFO
            )
     void getIpInfo(Object request, ResponseListener<ResponseIpInfo> responseListener);
   
    
    @EasyHttp(
            apiType=ApiType.DOWNLOADFILE,
            url = Constants.API_DOWNPIC,
            dummyData=DummyData.DUMMY_PIC
            )
    void downloadPic(Object request, ResponseListener<File> responseListener, File file);
    }

#####基本使用
如下所示，我们定义好API请求中需要传递的参数的数据结构。
    
    public class RequestIpInfo {
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    }
    
在我们调用接口的时候，这个数据结构中的值会自动帮我们被转换成如下的形式：
    
    http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json
    
接下来，我们只需要在回调函数中实现我们的业务逻辑。
    
    RequestIpInfo request = new RequestIpInfo();
        request.setFormat("json");
        ResponseListener responseListener = new ResponseListener<ResponseIpInfo>() {
    
            @Override
            public void onSuccess(ResponseIpInfo response) {
                super.onSuccess(response);
                EasyLog.d("response:" + new Gson().toJson(response));
            }
    
            @Override
            public void onFailure(int errorCode, String errorMessage) {
    
            }
    
            @Override
            public void onProgress(int bytesWritten, int totalSize) {
    
            }
    
        };
    
    ((TestService) ServiceFactory.create(this, ServiceType.NET, TestService.class)).getIpInfo(request, responseListener);
    
#####下载文件
下载文件和前面的方式类似。
    
    private void testEasyDownload() {
        final String picPath = Environment.getExternalStorageDirectory() + File.separator + "dddd.png";
        EasyLog.d("path: " + picPath);
        File picFile = new File(picPath);
        
        RequestDownPic request = new RequestDownPic();
        ResponseListener responseListener = new ResponseListener<File>() {
        
            @Override
            public void onSuccess(File response) {
                super.onSuccess(response);
            }
                
            @Override
            public void onFailure(int errorCode, String errorMessage) {
    
            }
    
            @Override
            public void onProgress(int bytesWritten, int totalSize) {
    
            }
        };
    
        ((TestService) ServiceFactory.create(this, ServiceType.NET, TestService.class)).downloadPic(request, responseListener, picFile);
    }
    
#####返回假数据    
要本地测试，返回我们定义的假数据，只需要在定义API接口的时候声明dummyData，然后创建一个ServiceType.Dummy类型的Service就可以了
    
    ((TestService) ServiceFactory.create(this, ServiceType.DUMMY, TestService.class)).downloadPic(request, responseListener, picFile);
    
#####缓存
程序可以将API请求得到的数据缓存到本地，在请求API的时候，可以先查询本地缓存，如果缓存没有，再查询服务器。
也很方便，值需创建一个ServiceType.CACHE的Service就可以了。
    
    ((TestService) ServiceFactory.create(this, ServiceType.CACHE, TestService.class)).downloadPic(request, responseListener, picFile);




  
