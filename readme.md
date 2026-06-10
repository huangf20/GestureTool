# GestureTool

基于 Android 摄像头与 AI 手势识别的隔空控制工具。

## 项目简介

GestureTool 是一个基于 Android 平台开发的手势交互工具，通过摄像头实时识别用户手势，并结合 Android AccessibilityService 实现对系统的模拟点击、滑动、返回等操作，从而实现非接触式手机控制。

用户无需触摸屏幕，仅通过手势即可完成常见操作，适用于智能终端交互、无接触控制、人机交互研究以及 Android AI 应用开发实践。

---

## 功能规划

### 已完成

* [x] AccessibilityService 接入
* [x] 模拟点击
* [x] 模拟滑动
* [x] 返回键控制
* [x] Home 键控制
* [x] 悬浮窗框架
* [x] ADB 广播调试接口

### 开发中

* [ ] CameraX 视频采集
* [ ] 手部检测
* [ ] 手势识别
* [ ] 手势映射控制

### 计划实现

#### 隔空滑动

| 手势   | 操作   |
| ---- | ---- |
| 手掌上移 | 页面上滑 |
| 手掌下移 | 页面下滑 |
| 手掌左移 | 页面左滑 |
| 手掌右移 | 页面右滑 |

#### 隔空点击

| 手势    | 操作   |
| ----- | ---- |
| 握拳    | 点击   |
| 长时间握拳 | 长按   |
| OK 手势 | 返回   |
| 五指张开  | Home |

#### 远程鼠标模式

| 手势   | 操作     |
| ---- | ------ |
| 张开手掌 | 鼠标移动   |
| 握拳   | 鼠标左键按下 |
| 松开手掌 | 鼠标左键释放 |
| 双手张开 | 鼠标滚轮模式 |

---

## 技术架构

```text
CameraX
    ↓
AI手势识别
    ↓
手势映射模块
    ↓
AccessibilityService
    ↓
Android系统控制
```

---

## 技术方案

### 方案一：YOLOv8 + 分类模型

#### 手部检测

目标：

输出手部矩形框：

```text
x
y
width
height
confidence
```

推荐模型：

* YOLOv8n
* YOLOv5n

数据集：

* EgoHands Dataset

部署流程：

```text
PyTorch
    ↓
ONNX
    ↓
NCNN
    ↓
Android
```

#### 手势分类

检测到手部后：

```text
手部裁剪
    ↓
分类模型
    ↓
输出手势类别
```

分类：

```text
open_hand
fist
```

推荐模型：

* MobileNetV3
* EfficientNet Lite

训练数据规模：

```text
1000~3000张
```

即可获得较高准确率。

---

### 方案二：MediaPipe Hand Landmarker（推荐）

利用 MediaPipe Hand Landmarker 直接输出手部关键点：

```text
21个手部关键点
```

通过关键点计算手指弯曲程度：

```text
五指伸直
    ↓
open_hand

五指弯曲
    ↓
fist
```

优势：

* 无需训练分类模型
* 推理速度快
* 模型体积小
* Android 集成方便
* 适合快速验证产品原型

技术架构：

```text
CameraX
    ↓
MediaPipe Hand Landmarker
    ↓
21个关键点
    ↓
手势规则判断
    ↓
AccessibilityService
```

---

## 项目结构

```text
app
├── activity
├── service
├── accessibility
├── camera
├── gesture
├── floatingwindow
├── receiver
└── utils
```

---

## 调试方式

### ADB 广播调试

模拟点击：

```bash
adb shell am broadcast -a com.yellowbee.ACTION_CLICK --ef x 540 --ef y 960
```

模拟返回：

```bash
adb shell am broadcast -a com.yellowbee.ACTION_BACK
```

模拟滑动：

```bash
adb shell am broadcast -a com.yellowbee.ACTION_SWIPE --ef startX 300 --ef startY 1200 --ef endX 300 --ef endY 600
```

---

## 技术栈

* Kotlin
* Android AccessibilityService
* CameraX
* MediaPipe
* YOLOv8
* ONNX
* NCNN
* Material Design 3

---

## 应用场景

* 隔空操作手机
* 智能终端交互
* 无接触控制场景
* 智慧展厅设备
* 教学演示设备
* RK3399 边缘计算终端
* AI 人机交互实验

---

## 后续计划

* [ ] 多手势识别
* [ ] 自定义手势映射
* [ ] 手势宏命令
* [ ] 蓝牙遥控模式
* [ ] Windows 鼠标控制
* [ ] Linux 桌面控制
* [ ] RK3399 适配优化
* [ ] Web 控制台

---

## License

MIT License
