import { useState } from 'react'
import { Form, Input, Button, Card, message, Tabs } from 'antd'
import { UserOutlined, LockOutlined } from '@ant-design/icons'
import { useNavigate } from 'react-router-dom'
import { userApi } from '../../services/api'
import { useUserStore } from '../../store'
import './index.css'

const Login = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)
  const [activeTab, setActiveTab] = useState('login')
  const { setToken, setUserInfo } = useUserStore()

  const handleLogin = async (values: { username: string; password: string }) => {
    setLoading(true)
    try {
      const res = await userApi.login(values)
      setToken(res.data.token)
      setUserInfo(res.data.user)
      message.success('登录成功')
      navigate('/home')
    } catch (error) {
      console.error('登录失败:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleRegister = async (values: {
    username: string
    password: string
    confirmPassword: string
    nickname?: string
  }) => {
    setLoading(true)
    try {
      await userApi.register(values)
      message.success('注册成功，请登录')
      setActiveTab('login')
    } catch (error) {
      console.error('注册失败:', error)
    } finally {
      setLoading(false)
    }
  }

  const tabItems = [
    {
      key: 'login',
      label: '登录',
      children: (
        <Form
          name="login"
          onFinish={handleLogin}
          autoComplete="off"
          size="large"
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input
              prefix={<UserOutlined />}
              placeholder="用户名"
            />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="密码"
            />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              登录
            </Button>
          </Form.Item>
        </Form>
      ),
    },
    {
      key: 'register',
      label: '注册',
      children: (
        <Form
          name="register"
          onFinish={handleRegister}
          autoComplete="off"
          size="large"
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input
              prefix={<UserOutlined />}
              placeholder="用户名"
            />
          </Form.Item>

          <Form.Item
            name="nickname"
          >
            <Input
              placeholder="昵称（可选）"
            />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="密码"
            />
          </Form.Item>

          <Form.Item
            name="confirmPassword"
            dependencies={['password']}
            rules={[
              { required: true, message: '请确认密码' },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue('password') === value) {
                    return Promise.resolve()
                  }
                  return Promise.reject(new Error('两次输入的密码不一致'))
                },
              }),
            ]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="确认密码"
            />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              注册
            </Button>
          </Form.Item>
        </Form>
      ),
    },
  ]

  return (
    <div className="login-container">
      <Card className="login-card">
        <div className="login-header">
          <h1>AI 学习助手</h1>
          <p>让学习更智能、更高效</p>
        </div>
        <Tabs activeKey={activeTab} onChange={setActiveTab} items={tabItems} centered />
      </Card>
    </div>
  )
}

export default Login
