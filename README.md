# TrackerApp
美呗技术部埋点解决方案
# How to use
### Step 1. Add the JitPack repository to your build file
```
buildscript {
  dependencies {
    classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
  }
}

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2. Add the dependency
```
dependencies {
	   implementation 'com.github.ning767566973:TrackerApp:v1.2'
}
```

### Step 3. Application init
```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TrackerSDK.getInstance().init(this, "你的应用APPID");
    }
}

```
### Step 4. Configuration parameters
设置参数（比如登录成功后需要把userid传过来）
```
 // 设置配置信息
TrackerSDK.getInstance().addCustomTracker(“配置对象”);
//设置登录用户id
TrackerSDK.getInstance().setUserId("登录的userid”);
```

### Step 5. Activity add annotation or event add annotation
```
/**
 * Activity添加注解ActivityTrace（暂时不支持fragment）
 * 方法点添加注解ClickTrace
 */
@ActivityTrace(pageId = "页面id")
public class FirstActivity extends AppCompatActivity {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @ClickTrace(eventId = "事件id")
    public void method() {
      
    }
}

```
## 自定义事件
```
CollectBody collectBody = TrackerSDK.getInstance().creatCommonCollect();
//修改collectBody值
//collectBody.setFrom("XXX");
        
TrackerSDK.getInstance().addCustomTracker(collectBody);
```
