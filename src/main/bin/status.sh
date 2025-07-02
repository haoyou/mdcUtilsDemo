#!/bin/bash

# =============================================
# 应用进程状态查询脚本
# 用法：./app-status.sh
# =============================================

# 定义应用标识关键词（与启动脚本保持一致）
APP_KEYWORD="mdcUtilsDemo"

# 执行进程查询（使用防自匹配技巧）
echo "🔍 正在查询应用进程..."
ps_output=$(ps -ef | grep "[${APP_KEYWORD:0:1}]${APP_KEYWORD:1}")

# 处理查询结果
if [ -n "$ps_output" ]; then
  echo "✅ 应用正在运行："
  echo "$ps_output"
  
  # 显示资源占用
  pid=$(echo "$ps_output" | awk '{print $2}')
  echo -e "\n📊 资源监控："
  top -bn1 -p $pid | tail -n 1
else
  echo "❌ 未找到运行中的应用进程"
  echo "提示：请检查应用是否已启动"
fi
