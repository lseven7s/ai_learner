import { Layout, Menu } from 'antd'
import { Outlet, useNavigate, useLocation } from 'react-router-dom'
import {
  HomeOutlined,
  FileTextOutlined,
  CalendarOutlined,
  UserOutlined,
  LogoutOutlined,
} from '@ant-design/icons'
import { useUserStore } from './store'
import './App.css'

const { Header, Content, Footer, Sider } = Layout

function App() {
  const navigate = useNavigate()
  const location = useLocation()
  const { logout, userInfo } = useUserStore()

  const menuItems = [
    {
      key: '/home',
      icon: <HomeOutlined />,
      label: '首页',
    },
    {
      key: '/materials',
      icon: <FileTextOutlined />,
      label: '资料管理',
    },
    {
      key: '/plans',
      icon: <CalendarOutlined />,
      label: '学习计划',
    },
    {
      key: '/profile',
      icon: <UserOutlined />,
      label: '个人中心',
    },
  ]

  const handleMenuClick = ({ key }: { key: string }) => {
    if (key === 'logout') {
      logout()
      navigate('/login')
    } else {
      navigate(key)
    }
  }

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider
        width={200}
        style={{
          overflow: 'auto',
          height: '100vh',
          position: 'fixed',
          left: 0,
          top: 0,
          bottom: 0,
        }}
      >
        <div style={{ height: 64, display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'white', fontSize: '1.2rem', fontWeight: 'bold' }}>
          AI 学习助手
        </div>
        <Menu
          mode="inline"
          selectedKeys={[location.pathname]}
          items={menuItems}
          onClick={handleMenuClick}
        />
        <div style={{ position: 'absolute', bottom: 20, left: 0, right: 0, padding: '0 24px' }}>
          <Menu.Item
            key="logout"
            icon={<LogoutOutlined />}
            onClick={handleMenuClick}
          >
            退出登录
          </Menu.Item>
        </div>
      </Sider>
      <Layout style={{ marginLeft: 200 }}>
        <Header style={{ padding: '0 24px', background: '#fff', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <span style={{ fontSize: '1.2rem', fontWeight: 'bold' }}>欢迎，{userInfo?.nickname || userInfo?.username}</span>
        </Header>
        <Content style={{ margin: '24px', overflow: 'auto' }}>
          <div style={{ padding: 24, background: '#fff', minHeight: 360 }}>
            <Outlet />
          </div>
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          AI 学习助手 ©{new Date().getFullYear()}
        </Footer>
      </Layout>
    </Layout>
  )
}

export default App
