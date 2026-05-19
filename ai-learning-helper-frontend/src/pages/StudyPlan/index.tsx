import { useEffect, useState } from 'react'
import {
  Table,
  Button,
  Modal,
  Form,
  Input,
  DatePicker,
  Space,
  Popconfirm,
  message,
  Tag,
  Card,
  Select,
  InputNumber,
  List,
} from 'antd'
import {
  PlusOutlined,
  EditOutlined,
  DeleteOutlined,
  CheckCircleOutlined,
  RobotOutlined,
} from '@ant-design/icons'
import type { DatePickerProps } from 'antd'
import dayjs from 'dayjs'
import { planApi, checkinApi } from '../../services/api'
import type { StudyPlanVO, StudyCheckinVO } from '../../services/api'

const { TextArea } = Input
const { RangePicker } = DatePicker
const { Option } = Select

const StudyPlan = () => {
  const [plans, setPlans] = useState<StudyPlanVO[]>([])
  const [checkins, setCheckins] = useState<StudyCheckinVO[]>([])
  const [loading, setLoading] = useState(false)
  const [modalVisible, setModalVisible] = useState(false)
  const [checkinModalVisible, setCheckinModalVisible] = useState(false)
  const [generateModalVisible, setGenerateModalVisible] = useState(false)
  const [editingPlan, setEditingPlan] = useState<StudyPlanVO | null>(null)
  const [selectedPlan, setSelectedPlan] = useState<StudyPlanVO | null>(null)
  const [form] = Form.useForm()
  const [checkinForm] = Form.useForm()
  const [generateForm] = Form.useForm()

  const fetchPlans = async () => {
    setLoading(true)
    try {
      const res = await planApi.list()
      setPlans(res.data || [])
    } catch (error) {
      message.error('获取学习计划失败')
    } finally {
      setLoading(false)
    }
  }

  const fetchCheckins = async (planId?: number) => {
    try {
      const res = await checkinApi.list(planId)
      setCheckins(res.data || [])
    } catch (error) {
      message.error('获取打卡记录失败')
    }
  }

  useEffect(() => {
    fetchPlans()
  }, [])

  const handleAdd = () => {
    setEditingPlan(null)
    form.resetFields()
    setModalVisible(true)
  }

  const handleEdit = (record: StudyPlanVO) => {
    setEditingPlan(record)
    form.setFieldsValue({
      title: record.title,
      description: record.description,
      dateRange: [dayjs(record.startDate), dayjs(record.endDate)],
      status: record.status,
    })
    setModalVisible(true)
  }

  const handleDelete = async (id: number) => {
    try {
      await planApi.delete(id)
      message.success('删除成功')
      fetchPlans()
    } catch (error) {
      message.error('删除失败')
    }
  }

  const handleSubmit = async (values: any) => {
    try {
      const data = {
        title: values.title,
        description: values.description,
        startDate: values.dateRange[0].format('YYYY-MM-DD'),
        endDate: values.dateRange[1].format('YYYY-MM-DD'),
        status: values.status || 0,
      }
      if (editingPlan) {
        await planApi.update(editingPlan.id, data)
        message.success('更新成功')
      } else {
        await planApi.create(data)
        message.success('创建成功')
      }
      setModalVisible(false)
      fetchPlans()
    } catch (error) {
      message.error(editingPlan ? '更新失败' : '创建失败')
    }
  }

  const handleCheckin = (record: StudyPlanVO) => {
    setSelectedPlan(record)
    checkinForm.resetFields()
    fetchCheckins(record.id)
    setCheckinModalVisible(true)
  }

  const handleSubmitCheckin = async (values: any) => {
    if (!selectedPlan) return
    try {
      await checkinApi.checkin({
        planId: selectedPlan.id,
        content: values.content,
      })
      message.success('打卡成功')
      setCheckinModalVisible(false)
      fetchCheckins(selectedPlan.id)
    } catch (error) {
      message.error('打卡失败')
    }
  }

  const handleGenerate = async (values: any) => {
    try {
      const res = await planApi.generate({
        topic: values.topic,
        duration: values.duration,
      })
      message.success('AI 生成成功')
      setGenerateModalVisible(false)
      fetchPlans()
    } catch (error) {
      message.error('AI 生成失败')
    }
  }

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

  const columns = [
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title',
    },
    {
      title: '描述',
      dataIndex: 'description',
      key: 'description',
      ellipsis: true,
    },
    {
      title: '开始日期',
      dataIndex: 'startDate',
      key: 'startDate',
      width: 120,
    },
    {
      title: '结束日期',
      dataIndex: 'endDate',
      key: 'endDate',
      width: 120,
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      width: 100,
      render: (status: number) => getStatusTag(status),
    },
    {
      title: '操作',
      key: 'action',
      width: 280,
      render: (_: any, record: StudyPlanVO) => (
        <Space size="small">
          <Button
            type="primary"
            size="small"
            icon={<CheckCircleOutlined />}
            onClick={() => handleCheckin(record)}
            disabled={record.status !== 0}
          >
            打卡
          </Button>
          <Button
            size="small"
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
          >
            编辑
          </Button>
          <Popconfirm
            title="确定要删除这个计划吗？"
            onConfirm={() => handleDelete(record.id)}
            okText="确定"
            cancelText="取消"
          >
            <Button type="link" danger size="small" icon={<DeleteOutlined />}>
              删除
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ]

  return (
    <div>
      <Card>
        <Space style={{ marginBottom: 16 }}>
          <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
            创建计划
          </Button>
          <Button
            icon={<RobotOutlined />}
            onClick={() => {
              generateForm.resetFields()
              setGenerateModalVisible(true)
            }}
          >
            AI 生成计划
          </Button>
        </Space>

        <Table
          columns={columns}
          dataSource={plans}
          rowKey="id"
          loading={loading}
        />
      </Card>

      <Modal
        title={editingPlan ? '编辑学习计划' : '创建学习计划'}
        open={modalVisible}
        onOk={() => form.submit()}
        onCancel={() => setModalVisible(false)}
        okText="确定"
        cancelText="取消"
      >
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
          <Form.Item
            name="title"
            label="标题"
            rules={[{ required: true, message: '请输入标题' }]}
          >
            <Input placeholder="请输入标题" />
          </Form.Item>
          <Form.Item name="description" label="描述">
            <TextArea rows={4} placeholder="请输入描述" />
          </Form.Item>
          <Form.Item
            name="dateRange"
            label="日期范围"
            rules={[{ required: true, message: '请选择日期范围' }]}
          >
            <RangePicker style={{ width: '100%' }} />
          </Form.Item>
          {editingPlan && (
            <Form.Item name="status" label="状态">
              <Select>
                <Option value={0}>进行中</Option>
                <Option value={1}>已完成</Option>
                <Option value={2}>已暂停</Option>
              </Select>
            </Form.Item>
          )}
        </Form>
      </Modal>

      <Modal
        title="学习打卡"
        open={checkinModalVisible}
        onOk={() => checkinForm.submit()}
        onCancel={() => setCheckinModalVisible(false)}
        okText="确定"
        cancelText="取消"
      >
        <div style={{ marginBottom: 16 }}>
          <h4>{selectedPlan?.title}</h4>
        </div>
        <Form form={checkinForm} layout="vertical" onFinish={handleSubmitCheckin}>
          <Form.Item name="content" label="打卡内容">
            <TextArea rows={4} placeholder="记录今天的学习内容..." />
          </Form.Item>
        </Form>
        <div style={{ marginTop: 16 }}>
          <h5>历史打卡记录</h5>
          <List
            dataSource={checkins}
            renderItem={(item) => (
              <List.Item>
                <List.Item.Meta
                  title={item.checkinDate}
                  description={item.content}
                />
              </List.Item>
            )}
          />
        </div>
      </Modal>

      <Modal
        title="AI 生成学习计划"
        open={generateModalVisible}
        onOk={() => generateForm.submit()}
        onCancel={() => setGenerateModalVisible(false)}
        okText="生成"
        cancelText="取消"
      >
        <Form form={generateForm} layout="vertical" onFinish={handleGenerate}>
          <Form.Item
            name="topic"
            label="学习主题"
            rules={[{ required: true, message: '请输入学习主题' }]}
          >
            <Input placeholder="例如：Python 入门、前端开发、考研数学" />
          </Form.Item>
          <Form.Item
            name="duration"
            label="学习天数"
            rules={[{ required: true, message: '请输入学习天数' }]}
          >
            <InputNumber min={1} max={365} style={{ width: '100%' }} placeholder="计划学习多少天" />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default StudyPlan
