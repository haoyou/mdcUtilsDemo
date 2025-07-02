#!/bin/bash

cd "$(dirname "$0")"

LIB_DIR="../lib"
CONFIG_DIR="../config"
LOG_DIR="../../logs"

# 关键修改：使用精确文件名或更宽泛的通配符
MAIN_JAR="mdcUtilsDemo*.jar"  # 修改1：移除版本号前的短横线
# 或 MAIN_JAR="*.jar"         # 修改2：匹配所有JAR文件

mkdir -p "$LOG_DIR"

# 增强错误检查：打印目录内容
if [ -z "$(ls $LIB_DIR/$MAIN_JAR 2>/dev/null)" ]; then
  echo "[ERROR] 未找到主JAR文件: $LIB_DIR/$MAIN_JAR"
  echo "当前lib目录内容:"
  ls -l "$LIB_DIR"  # 打印目录内容辅助调试
  exit 1
fi

# 修改3：处理多个JAR的情况（取最新）
JAR_FILE=$(ls -t $LIB_DIR/$MAIN_JAR | head -1)  # 按时间排序取最新

echo "正在启动应用: $(basename $JAR_FILE)"
nohup java -jar "$JAR_FILE" \
  --spring.config.location="$CONFIG_DIR/" \
  > "$LOG_DIR/app.log" 2>&1 &

# ... 剩余部分保持不变 ...
