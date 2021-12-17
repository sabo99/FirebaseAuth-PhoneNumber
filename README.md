# <img src="https://img.icons8.com/color/48/000000/firebase.png"/>  Firebase Authentication with Phone Number

Authentication Android application using <a href="https://firebase.google.com/">`Firebase`</a> with the login system using **Phone Number**


### **App Features Firebase Authentication with Phone Number** :

- **_Firebase_**
  - Authentication with `PhoneNumber`
  - Verification with `SmsCode`
- **_Country Code Picker_**
  - Change Country Code | Ex : `(+62) ID`
  - Auto Detect Country Code

## Important!

### Add Firebase to your Android project go to <a href="https://firebase.google.com/docs/android/setup">`Firebase Setup`</a>

<br>

## Design UI App Firebase Authentication with Phone Number

### **`Preview Firebase Auth with Phone Number`**

<img src="Screenshot_App/Screenshot 2021-12-16 213623.png" height="500"/>

### **`Preview UI FirebaseAuth-PhoneNumber`**
| Name Page    | Preview UI            |  
| :----------: | :--------------------: | 
| `OTP Send`         | <img src="Screenshot_App/1639664111362.jpg" height="500"/> | 
| `OTP Send with Phone Number`         | <img src="Screenshot_App/1639664111368.jpg" height="500"/> | 
| `Country Code Picker`  | <img src="Screenshot_App/1639664111355.jpg" height="500"/> | 
| `OTP Verify`  | <img src="Screenshot_App/1639664111347.jpg" height="500"/> | 
| `OTP Verify - Resend SmsCode`  | <img src="Screenshot_App/1639664111341.jpg" height="500"/> | 
| `Home`  | <img src="Screenshot_App/1639664111376.jpg" height="500"/> | 

<br>

## Result on Firebase Console

### **`Authentication`**
<img src="Screenshot_App/Screenshot 2021-12-16 212044.png"/>



<br>

## Dependencies used

```groovy
   /** Firebase */
    implementation platform('com.google.firebase:firebase-bom:29.0.2')
    implementation 'com.google.firebase:firebase-analytics-ktx'
    implementation 'com.google.firebase:firebase-auth-ktx'

    implementation 'com.hbb20:ccp:2.5.0'

```

### Integration Step Used Binding in Kotlin

1. Add **viewBinding `true`** <a href="./app/build.gradle">`build.gralde (Module)`</a>

```groovy
android {
   ...
   buildFeatures {
      viewBinding true
   }
}
```

2. Activity Kotlin Class

```kotlin
class MainActivity : AppCompatActivity() {

    /** Add this */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Add this */
        binding = ActivityMainBinding.inflate(layoutInflater)

        /** Change this */
        setContentView(binding.root)

        /** Without findViewById */
        binding.textView.text = "Bye bye findViewById"
    }
}
```

3. Activity Java Class

```java
public class MainActivity extends AppCompatActivity {

    /** Add this */
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Add this */
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        /** Change this */
        setContentView(binding.getRoot());

        /** Without findViewById */
        binding.textView.setText("Bye bye findViewById");
    }
}
```

**binding** in kotlin can be used directly without initializing **findViewById** on widgets in layout xml