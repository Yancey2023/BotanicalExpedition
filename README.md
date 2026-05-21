# Botanical Expedition — 植物科考助手

<p align="center">
  <em>一款专为植物学研究人员和野外科考队员设计的 Android 数据采集工具</em>
</p>

> **开发文档**: [安卓植物科考 APP 开发文档](./安卓植物科考%20APP%20开发文档.docx)

---

## 项目概述

**Botanical Expedition** 是一款面向植物科考人员的移动端应用，支持野外植物数据采集、拍照记录、自动 GPS 定位、离线存储与数据同步。项目采用纯 Kotlin + Jetpack Compose 开发，完全离线可用。

### 目标用户

| 用户类型 | 核心需求 |
|---------|---------|
| 植物学研究人员 | 精确记录植物分类、形态、分布信息 |
| 野外科考队员 | 快速、低负担的数据采集 |
| 生态调查人员 | 标准化调查表格和批量导出 |
| 保护地巡护员 | 简单易用的拍照 + 定位记录 |

### 解决痛点

- **纸质记录效率低** → 数字化采集，3 步完成一条记录
- **照片与记录对不上** → 照片自动绑定植物记录
- **坐标手工录入易错** → 拍摄时自动获取 GPS 坐标
- **数据整理耗时** → 支持导出为 CSV/Excel
- **离线不可用** → 核心业务 100% 离线可用，联网后自动同步

---

## 技术栈

| 类别 | 技术选型 |
|------|---------|
| 语言 | Kotlin 纯开发（无 Java） |
| UI | Jetpack Compose + Material 3 |
| 数据库 | Room（本地 SQLite） |
| 网络 | Retrofit + OkHttp + Kotlin Serialization |
| 异步 | Kotlin Coroutines |
| 构建 | Gradle KTS + Version Catalog |
| 最低版本 | Android 8.0 (API 26) |
| 目标版本 | Android 14 (API 34) |

---

## 功能模块

### 1. 采集模块（V1.0 核心）

- 新建科考记录（自动生成本地唯一 ID）
- 拍照记录植物（照片自动压缩至 2MB 以内）
- 自动获取 GPS 坐标（经纬度、海拔、精度）
- 手动输入植物信息（名称、科属、形态描述等）
- 保存记录到本地 Room 数据库

### 2. 识别模块（V1.1 可选）

- 图库识别：从相册选择照片，联网调用识别 API
- 特征检索：按叶形、花色等特征离线筛选植物库

### 3. 数据管理模块

- 记录列表（分页展示，按时间/地点/植物筛选）
- 记录详情（完整展示照片 + 数据 + 地图坐标）
- 编辑/删除记录
- 数据导出（CSV + 照片 ZIP 包）

### 4. 设置与同步模块

- 账号登录/注册
- 手动/自动同步
- 离线模式开关
- 存储清理

---

## 项目结构

```
app/src/main/kotlin/com/example/botanicalexpedition/
├── MainActivity.kt                 # 主 Activity（Compose 入口）
├── data/
│   ├── api/
│   │   ├── PlantApiService.kt      # Retrofit 接口定义
│   │   └── RetrofitClient.kt       # Retrofit 客户端配置
│   ├── local/
│   │   ├── AppDatabase.kt          # Room 数据库
│   │   ├── PlantDao.kt             # 数据访问对象
│   │   └── PlantEntity.kt          # 数据实体
│   ├── model/
│   │   └── Plant.kt                # 领域模型
│   └── repository/
│       └── PlantRepository.kt     # 数据仓库（本地+远程）
└── ui/
    └── theme/
        ├── Color.kt                # 主题色
        ├── Theme.kt                # Material 3 主题
        └── Type.kt                 # 排版
```

---

## 页面清单

| 页面 | 说明 | 状态 |
|------|------|------|
| 启动页 | Logo 展示，离线数据检查 | 待开发 |
| 登录/注册页 | 手机号 + 密码登录 | 待开发 |
| 主页（采集入口） | 新建记录 + 最近记录 | 待开发 |
| 记录编辑页 | 拍照、填信息、保存 | 待开发 |
| 记录列表页 | 时间/地点/筛选历史 | 待开发 |
| 记录详情页 | 完整展示单条记录 | 待开发 |
| 识别页 | 拍照/选图识别植物 | V1.1 |
| 识别结果页 | Top3 识别结果展示 | V1.1 |
| 设置页 | 离线模式、同步、存储 | 待开发 |
| 同步状态页 | 待同步记录数、进度 | 待开发 |

---

## 数据库设计

### Record（植物记录表）

| 字段 | 类型 | 说明 |
|------|------|------|
| recordId | String (UUID) | 主键，本地唯一标识 |
| plantName | String | 植物名称（必填） |
| family | String | 科名 |
| genus | String | 属名 |
| description | String | 形态描述 |
| habitat | String | 生境描述 |
| latitude | Double | 纬度（必填） |
| longitude | Double | 经度（必填） |
| altitude | Double | 海拔（米） |
| accuracy | Float | 定位精度（米） |
| createTime | Long | 创建时间戳 |
| modifyTime | Long | 修改时间戳 |
| syncStatus | Int | 0=未同步 1=已同步 2=同步失败 |
| userId | String | 所属用户 ID |

### Photo（照片表）

| 字段 | 类型 | 说明 |
|------|------|------|
| photoId | String (UUID) | 主键 |
| recordId | String | 外键，关联 Record |
| localPath | String | 本地绝对路径 |
| remoteUrl | String | 云端 URL |
| isSync | Boolean | 是否已同步 |
| createTime | Long | 拍摄时间 |

> 当前数据库中已实现 `plants` 表作为初始版本，后续将迁移至上述完整设计。

---

## 快速开始

### 前置要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17+
- Android SDK 34+

### 构建与运行

```bash
# 克隆项目
git clone <repository-url>

# 在 Android Studio 中打开项目根目录
# 等待 Gradle 同步完成

# 直接运行
./gradlew assembleDebug

# 安装到设备
./gradlew installDebug
```

---

## 开发计划

- **V1.0** — 核心采集流程（拍照 + 定位 + 记录 + 离线存储 + 数据导出）
- **V1.1** — 植物识别、数据同步、账号系统

---

## 权限说明

| 权限 | 用途 | 申请时机 |
|------|------|---------|
| CAMERA | 拍照记录植物 | 首次点击拍照 |
| ACCESS_FINE_LOCATION | 获取 GPS 坐标 | 首次新建记录 |
| INTERNET | 数据同步、登录 | 启动时 |
| ACCESS_NETWORK_STATE | 检测网络状态 | 启动时 |
| READ/WRITE_EXTERNAL_STORAGE | 相册选择/导出 | 使用时申请 |

---

## 许可证

该项目仅供学习交流使用。
