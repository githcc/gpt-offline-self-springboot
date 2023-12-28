# gpt-offline-self-springboot
个人使用的离线版gpt，模型需要自己另外下载

## 模型地址
1. [mistral-7b-openorca.Q4_0.gguf](https://119.91.23.137/d/%F0%9F%88%B4%E6%88%91%E7%9A%84%E9%98%BF%E9%87%8C%E5%88%86%E4%BA%AB/Tacit0926/mistral-7b-openorca.mp4) 下载的文件为mp4，需要手动修改文件名称，阿里云盘限制不让分享gguf文件
2. [官网](https://gpt4all.io/index.html)

## 使用
1. 下载模型
2. 将修改模型路径

## TODO
1. 请求改为post，突破get报文限制 ok
2. 将响应改为流式，立即响应 ok
3. 支持多轮对话 ok
4. 处理内存泄露
5. 处理关闭连接
6. 使用gpu，加快响应速度

## 参考项目
[gpt4all](https://github.com/nomic-ai/gpt4all)