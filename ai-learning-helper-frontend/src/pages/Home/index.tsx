import { useEffect, useState } from 'react'
import { Card, Row, Col, Statistic, List, Button, message, Tag } from 'antd'
import {
  FileTextOutlined,
  CalendarOutlined,
  CheckCircleOutlined,
  BookOutlined,
} from '@ant-design/icons'
import { useNavigate } from 'react-router-dom'
import { materialApi, planApi, checkinApi } from '../../services/api'
import type { StudyMaterialVO, StudyPlanVO, StudyCheckinVO } from '../../services/api'

const Home = () => {
  const navigate = useNavigate()
  const [materials, setMaterials] = useState<StudyMaterialVO[]>([])
  const [plans, setPlans] = useState<StudyPlanVO[]>([])
  const [checkins, setCheckins] = useState<StudyCheckinVO[]>([])
  const [loading, setLoading] = useState(false)

  const fetchData = async () => {
    setLoading(true)
    try {
      const [materialsRes, plansRes, checkinsRes] = await Promise.all([
        materialApi.list(),
        planApi.list(),
        checkinApi.list(),
      ])
      setMaterials(materialsRes.data || [])
      setPlans(plansRes.data || [])
      setCheckins(checkinsRes.data || [])
    } catch (error) {
      message.error('获取数据失败')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  const getStatusTag = (status: number) => {
    switch (status) {
      case 0:
        return <Tag color="blue">进行中</Tag>
      case 1:
        return <Tag color="green">已完成</Tag>
      case 2:
        return <Tag color="red">已暂停</Tag>
      default:
        return <Tag>未知</Tag>
    }
  }

  return (
    <div>
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={6}>
          <Card>
            <Statistic
              title="学习资料"
              value={materials.length}
              prefix={<FileTextOutlined />}
              valueStyle={{ color: '#3f8600' }}
            />
          </Card>
        </Col>
        <Col span={6}>
          <Card>
            <Statistic
              title="学习计划"
              value={plans.length}
              prefix={<CalendarOutlined />}
              valueStyle={{ color: '#1890ff' }}
            />
          </Card>
        </Col>
        <Col span={6}>
          <Card>
            <Statistic
              title="打卡次数"
              value={checkins.length}
              prefix={<CheckCircleOutlined />}
              valueStyle={{ color: '#722ed1' }}
            />
          </Card>
        </Col>
        <Col span={6}>
          <Card>
            <Statistic
              title="进行中计划"
              value={plans.filter(p => p.status === 0).length}
              prefix={<BookOutlined />}
              valueStyle={{ color: '#fa8c16' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={16}>
        <Col span={12}>
          <Card
            title="最近学习计划"
            extra={
              <Button type="link" onClick={() => navigate('/plans')}>
                查看全部
              </Button>
            }
          >
            <List
              loading={loading}
              dataSource={plans.slice(0, 5)}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta
                    title={
                      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <span>{item.title}</span>
                        {getStatusTag(item.status)}
                      </div>
                    }
                    description={`${item.startDate} 至 ${item.endDate}`}
                  />
                </List.Item>
              )}
            />
          </Card>
        </Col>
        <Col span={12}>
          <Card
            title="最近学习资料"
            extra={
              <Button type="link" onClick={() => navigate('/materials')}>
                查看全部
              </Button>
            }
          >
            <List
              loading={loading}
              dataSource={materials.slice(0, 5)}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta
                    title={item.title}
                    description={item.description || item.fileType}
                  />
                </List.Item>
              )}
            />
          </Card>
        </Col>
      </Row>
    </div>
  )
}

export default Home
