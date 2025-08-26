# Android RenderScript-toolkit

基于Google官方[RenderScript-toolkit](https://github.com/android/renderscript-intrinsics-replacement-toolkit)编译，用于取代老旧的 `renderscript`。

该工具包提供了一系列高性能图像处理功能 如模糊、混合和调整大小。已弃用的RenderScript Intrinsics函数的完美替代品。

**支持 16K page size**

## 包含功能
- 混合模式
- 高斯模糊
- 颜色矩阵滤镜
- 盲卷积
- 直方图和直方图点
- LUT和LUT3D
- 调整大小
- YUV to RGB



## 添加依赖
```
implementation("io.github.limuyang2:renderscrip-toolkit:1.0.1")
```

## 使用方法
使用`ImageToolkit`调用各类方法。
```kotlin
// 例：高斯模糊
val newBitmap = ImageToolkit.blur(inputBitmap, 20)
```


