MyBan You - Android Studio Project (Kotlin)
Package: com.mybanyou.Gxyenn
Developer: Gxyenn

Features included:
- Region picker, numeric keyboard for phone input.
- Automatic date detection from device.
- Message template prefilled (report format) with placeholders replaced.
- Lottie loading animation while sending.
- Background & launcher icon placeholders (res/drawable/bg_custom.png and res/mipmap-anydpi-v26/ic_launcher.png).
- SMTP sending via JavaMail (BuildConfig fields in app/build.gradle).

Important:
- Replace SMTP credentials in app/build.gradle (defaultConfig buildConfigField) before building,
  or change EmailWorker to pull from secure backend.
- To add translations: create values-<lang>/strings.xml and include translated strings.
- Build with Android Studio: Open project root -> Build -> Build APK(s).