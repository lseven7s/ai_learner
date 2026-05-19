import { useEffect, useState } from 'react'
import {
  Card,
  Form,
  Input,
  Button,
  Avatar,
  message,
  Row,
  Col,
  Descriptions,
} from 'antd'
import { UserOutlined } from '@ant-design/icons'
import { userApi } from '../../services/api'
import { useUserStore } from '../../store'
import type { UserVO } from '../../services/api'

const Profile = () => {
  const [form] = Form.useForm()
  const { userInfo, setUserInfo } = useUserStore()
  const [loading, setLoading] = useState(false)

  const fetchUserInfo = async () => {
    try {
      const res = await userApi.getCurrentUser()
      setUserInfo(res.data)
      form.setFieldsValue({
        nickname: res.data.nickname,
        email: res.data.email,
      })
    } catch (error) {
      message.error('获取用户信息失败')
    }
  }

  useEffect(() => {
    fetchUserInfo()
  }, [])

  const handleSubmit = async (values: any) => {
    setLoading(true)
    try {
      await userApi.updateUser(values)
      message.success('更新成功')
      fetchUserInfo()
    } catch (error) {
      message.error('更新失败')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <Row gutter={24}>
        <Col span={8}>
          <Card>
            <div style={{ textAlign: 'center' }}>
              <Avatar
                size={120}
                icon={<UserOutlined />}
                src={userInfo?.avatar}
                style={{ marginBottom: 16 }}
              />
              <h2>{userInfo?.nickname || userInfo?.username}</h2>
              <p style={{ color: '#999' }}>@{userInfo?.username}</p>
            </div>
          </Card>
        </Col>
        <Col span={16}>
          <Card title="基本信息">
            <Descriptions column={1} style={{ marginBottom: 24 }}>
              <Descriptions.Item label="用户名">
                {userInfo?.username}
              </Descriptions.Item>
              <Descriptions.Item label="昵称">
                {userInfo?.nickname || '未设置'}
              </Descriptions.Item>
              <Descriptions.Item label="邮箱">
                {userInfo?.email || '未设置'}
              </Descriptions.Item>
              <Descriptions.Item label="注册时间">
                {userInfo?.createTime}
              </Descriptions.Item>
            </Descriptions>
          </Card>

          <Card title="编辑资料" style={{ marginTop: 16 }}>
            <Form
              form={form}
              layout="vertical"
              onFinish={handleSubmit}
              initialValues={{
                nickname: userInfo?.nickname,
                email: userInfo?.email,
              }}
            >
              <Form.Item name="nickname" label="昵称">
                <Input placeholder="请输入昵称" />
              </Form.Item>
              <Form.Item
                name="email"
                label="邮箱"
                rules={[
                  {
                    type: 'email',
                    message: '请输入有效的邮箱地址',
                  },
                ]}
              >
                <Input placeholder="请输入邮箱" />
              </Form.Item>
              <Form.Item>
                <Button type="primary" htmlType="submit" loading={loading}>
                  保存修改
                </Button>
              </Form.Item>
            </Form>
          </Card>
        </Col>
      </Row>
    </div>
  )
}

export default Profile
