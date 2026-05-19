import { useEffect, useState } from 'react'
import {
  Table,
  Button,
  Upload,
  Space,
  Input,
  Select,
  Popconfirm,
  message,
  Card,
} from 'antd'
import {
  UploadOutlined,
  DeleteOutlined,
  SearchOutlined,
  DownloadOutlined,
} from '@ant-design/icons'
import type { UploadProps } from 'antd'
import { materialApi } from '../../services/api'
import type { StudyMaterialVO } from '../../services/api'

const { Search } = Input
const { Option } = Select

const MaterialManage = () => {
  const [materials, setMaterials] = useState<StudyMaterialVO[]>([])
  const [loading, setLoading] = useState(false)
  const [keyword, setKeyword] = useState('')
  const [fileType, setFileType] = useState<string | undefined>()

  const fetchMaterials = async () => {
    setLoading(true)
    try {
      const res = await materialApi.list({ keyword, fileType })
      setMaterials(res.data || [])
    } catch (error) {
      message.error('获取资料列表失败')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchMaterials()
  }, [keyword, fileType])

  const uploadProps: UploadProps = {
    name: 'file',
    showUploadList: false,
    beforeUpload: async (file) => {
      const formData = new FormData()
      formData.append('file', file)
      formData.append('title', file.name)
      setLoading(true)
      try {
        await materialApi.upload(formData)
        message.success('上传成功')
        fetchMaterials()
      } catch (error) {
        message.error('上传失败')
      } finally {
        setLoading(false)
      }
      return false
    },
  }

  const handleDelete = async (id: number) => {
    try {
      await materialApi.delete(id)
      message.success('删除成功')
      fetchMaterials()
    } catch (error) {
      message.error('删除失败')
    }
  }

  const columns = [
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title',
    },
    {
      title: '文件类型',
      dataIndex: 'fileType',
      key: 'fileType',
      width: 120,
    },
    {
      title: '文件大小',
      dataIndex: 'fileSize',
      key: 'fileSize',
      width: 120,
      render: (size: number) => `${(size / 1024).toFixed(2)} KB`,
    },
    {
      title: '上传时间',
      dataIndex: 'createTime',
      key: 'createTime',
      width: 180,
    },
    {
      title: '操作',
      key: 'action',
      width: 150,
      render: (_: any, record: StudyMaterialVO) => (
        <Space size="small">
          <Button
            type="link"
            icon={<DownloadOutlined />}
            onClick={() => window.open(record.fileUrl)}
          >
            下载
          </Button>
          <Popconfirm
            title="确定要删除这个资料吗？"
            onConfirm={() => handleDelete(record.id)}
            okText="确定"
            cancelText="取消"
          >
            <Button type="link" danger icon={<DeleteOutlined />}>
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
        <Space style={{ marginBottom: 16 }} wrap>
          <Upload {...uploadProps}>
            <Button type="primary" icon={<UploadOutlined />} loading={loading}>
              上传资料
            </Button>
          </Upload>
          <Search
            placeholder="搜索资料"
            allowClear
            style={{ width: 300 }}
            prefix={<SearchOutlined />}
            onSearch={(value) => setKeyword(value)}
            onChange={(e) => !e.target.value && setKeyword('')}
          />
          <Select
            placeholder="筛选文件类型"
            style={{ width: 150 }}
            allowClear
            onChange={setFileType}
          >
            <Option value="pdf">PDF</Option>
            <Option value="doc">DOC</Option>
            <Option value="docx">DOCX</Option>
            <Option value="ppt">PPT</Option>
            <Option value="pptx">PPTX</Option>
            <Option value="xls">XLS</Option>
            <Option value="xlsx">XLSX</Option>
            <Option value="txt">TXT</Option>
            <Option value="image">图片</Option>
            <Option value="video">视频</Option>
            <Option value="other">其他</Option>
          </Select>
        </Space>

        <Table
          columns={columns}
          dataSource={materials}
          rowKey="id"
          loading={loading}
        />
      </Card>
    </div>
  )
}

export default MaterialManage
