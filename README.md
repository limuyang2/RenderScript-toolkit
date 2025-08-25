# Android RenderScript-toolkit

基于Google官方[toolkit](https://github.com/android/renderscript-intrinsics-replacement-toolkit)编译，用于取代老旧的 `renderscript`。

**支持 16K page size**

#### 添加依赖
```
implementation("io.github.limuyang2:renderscrip-toolkit:1.0.1")
```

#### 使用方法
```kotlin
val newBitmap = ImageToolkit.blur(inputBitmap, 20)
```


