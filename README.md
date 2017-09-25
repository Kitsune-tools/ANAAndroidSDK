# ANAAndroidSDK

### Steps To Integrate

#### Step 1: Download Mobile SDK

clone the SDK.
```bash
git clone https://github.com/Kitsune-tools/ANAAndroidSDK.git
```

### Step 2: Importing to Android Studio

1. From the menu bar, click **File -> Import Module**
2. Navigate to the **ANAAndroidSDK** directory that is contained in the **ANAAndroidSDK** repo
3. Select the **ANAAndroidSDK** file and click Finish
4. Make sure your **build.gradle** file has `compile project(':ANAAndroidSDK')` under `dependencies {}`
5. Make sure your project **build.gradle** file has 
'allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}'

### Step 3: Integrate ANA SDK with your app

1. Import the SDK
`import com.ana.managers.ChatbotManager;`
2. Initialize the SDK: `ChatbotManager.getInstance("ACTIVITY_CONTEXT").startChat("URL");`
