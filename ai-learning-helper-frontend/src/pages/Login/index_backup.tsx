<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AI 学习助手 - 诊断页面</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            max-width: 600px;
            width: 100%;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
            font-size: 28px;
        }
        .status {
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .warning {
            background: #fff3cd;
            color: #856404;
            border: 1px solid #ffeaa7;
        }
        .info {
            background: #d1ecf1;
            color: #0c5460;
            border: 1px solid #bee5eb;
        }
        h2 {
            color: #555;
            margin: 20px 0 10px;
            font-size: 18px;
        }
        .btn {
            display: inline-block;
            padding: 12px 24px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            margin: 10px 5px 0 0;
            transition: background 0.3s;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }
        .btn:hover {
            background: #5568d3;
        }
        .btn-secondary {
            background: #6c757d;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        code {
            background: #f4f4f4;
            padding: 2px 6px;
            border-radius: 3px;
            font-family: 'Courier New', monospace;
            font-size: 13px;
            color: #e83e8c;
        }
        ul {
            margin: 10px 0;
            padding-left: 20px;
        }
        li {
            margin: 5px 0;
            color: #555;
        }
        .test-result {
            margin-top: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 8px;
            font-family: monospace;
            font-size: 13px;
            max-height: 200px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔍 AI 学习助手 - 诊断页面</h1>
        
        <div id="status-container"></div>
        
        <h2>快速检查</h2>
        <button class="btn" onclick="testApi()">测试 API 连接</button>
        <button class="btn btn-secondary" onclick="clearStorage()">清除本地存储</button>
        <button class="btn btn-secondary" onclick="location.reload()">刷新页面</button>
        
        <div id="test-result" class="test-result" style="display:none;"></div>
        
        <h2>如果仍有问题</h2>
        <ul>
            <li>请打开浏览器开发者工具 (F12)</li>
            <li>查看 Console 标签页的错误信息</li>
            <li>查看 Network 标签页的网络请求</li>
            <li>确保后端服务已启动并运行在 8080 端口</li>
            <li>确保数据库迁移已完成</li>
        </ul>
        
        <h2>启动命令</h2>
        <p>后端：<code>cd ai-learning-helper-backend && mvn spring-boot:run</code></p>
        <p>前端：<code>cd ai-learning-helper-frontend && npm run dev</code></p>
    </div>

    <script>
        const statuses = [];
        
        function addStatus(message, type) {
            statuses.push({ message, type });
            render();
        }
        
        function render() {
            const container = document.getElementById('status-container');
            container.innerHTML = statuses.map(s => 
                `<div class="status ${s.type}">${s.message}</div>`
            ).join('');
        }
        
        function testApi() {
            const result = document.getElementById('test-result');
            result.style.display = 'block';
            result.innerHTML = '正在测试 API 连接...<br><br>';
            
            fetch('http://localhost:8080/api/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: 'test', password: 'test' })
            })
            .then(res => res.json())
            .then(data => {
                result.innerHTML += `✅ API 连接成功<br>响应: ${JSON.stringify(data, null, 2)}`;
            })
            .catch(err => {
                result.innerHTML += `❌ API 连接失败<br>错误: ${err.message}<br><br>可能的原因：<br>1. 后端未启动<br>2. 后端端口不是 8080<br>3. CORS 配置问题`;
            });
        }
        
        function clearStorage() {
            localStorage.clear();
            sessionStorage.clear();
            alert('本地存储已清除，请刷新页面');
        }
        
        // 初始化检查
        window.onload = function() {
            // 检查 localStorage
            const token = localStorage.getItem('token');
            if (token) {
                addStatus('✅ 发现登录令牌 (Token 存在)', 'success');
            } else {
                addStatus('⚠️ 未发现登录令牌 (这是正常的，如果您还未登录)', 'warning');
            }
            
            // 检查 localStorage 数据
            const userInfo = localStorage.getItem('user-storage');
            if (userInfo) {
                addStatus('✅ 发现用户信息', 'success');
            } else {
                addStatus('⚠️ 未发现用户信息', 'warning');
            }
            
            // 检查后端连接
            addStatus('🔄 尝试连接后端 API...', 'info');
            fetch('http://localhost:8080/api/user/login', {
                method: 'OPTIONS'
            })
            .then(() => {
                addStatus('✅ 后端 API 可访问', 'success');
            })
            .catch(err => {
                addStatus('❌ 后端 API 不可访问: ' + err.message, 'error');
                addStatus('💡 请确保后端服务已启动 (mvn spring-boot:run)', 'warning');
            });
        };
    </script>
</body>
</html>
