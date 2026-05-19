import request from '../utils/request'

export interface Result<T> {
  code: number
  message: string
  data: T
}

export interface PageResult<T> {
  records: T[]
  total: number
  size: number
  current: number
}

export interface UserVO {
  id: number
  username: string
  nickname: string
  email?: string
  avatar?: string
  phone?: string
  status?: number
  createTime: string
}

export interface LoginResponseVO {
  token: string
  user: UserVO
}

export interface StudyMaterialVO {
  id: number
  userId: number
  title: string
  description?: string
  fileUrl?: string
  fileType?: string
  category?: string
  createTime: string
}

export interface StudyPlanVO {
  id: number
  userId: number
  title: string
  description?: string
  startDate: string
  endDate: string
  status: number
  createTime: string
}

export interface StudyCheckinVO {
  id: number
  userId: number
  planId: number
  checkinDate: string
  studyContent?: string
  createTime: string
}

export const userApi = {
  login: (data: { username: string; password: string }) => {
    return request.post<LoginResponseVO>('/user/login', data)
  },
  register: (data: {
    username: string
    password: string
    confirmPassword: string
    nickname?: string
  }) => {
    return request.post<string>('/user/register', data)
  },
  getCurrentUser: () => {
    return request.get<UserVO>('/user/info')
  },
  updateUser: (data: { nickname?: string; email?: string; avatar?: string; phone?: string }) => {
    return request.put<UserVO>('/user/info', data)
  },
}

export const materialApi = {
  uploadFile: (file: File) => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post<string>('/study-material/file/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },
  getFileUrl: (fileName: string) => {
    return request.get<string>(`/study-material/file/${fileName}`)
  },
  upload: (data: {
    title: string
    description?: string
    fileUrl: string
    fileType?: string
    category?: string
  }) => {
    return request.post<number>('/study-material/upload', data)
  },
  uploadWithFile: async (file: File) => {
    const fileRes = await materialApi.uploadFile(file)
    const fileName = fileRes.data
    const urlRes = await materialApi.getFileUrl(fileName)
    const ext = file.name.includes('.') ? file.name.split('.').pop() : ''
    return materialApi.upload({
      title: file.name,
      fileUrl: urlRes.data,
      fileType: ext,
    })
  },
  list: (params?: { title?: string; fileType?: string; pageNum?: number; pageSize?: number }) => {
    return request.get<PageResult<StudyMaterialVO>>('/study-material/list', { params })
  },
  delete: (id: number) => {
    return request.delete(`/study-material/${id}`)
  },
}

export const planApi = {
  create: (data: { title: string; description?: string; startDate: string; endDate: string }) => {
    return request.post<number>('/study-plans', data)
  },
  list: () => {
    return request.get<StudyPlanVO[]>('/study-plans')
  },
  update: (
    id: number,
    data: { title?: string; description?: string; startDate?: string; endDate?: string; status?: number }
  ) => {
    return request.put<void>('/study-plans', { id, ...data })
  },
  delete: (id: number) => {
    return request.delete(`/study-plans/${id}`)
  },
  generate: (data: { goal: string; duration: string }) => {
    return request.post<StudyPlanVO>('/study-plans/generate', data)
  },
}

export const checkinApi = {
  checkin: (data: { planId: number; studyContent?: string }) => {
    return request.post<StudyCheckinVO>('/study-checkins', data)
  },
  listByPlan: (planId: number) => {
    return request.get<StudyCheckinVO[]>(`/study-checkins/plan/${planId}`)
  },
  listMy: () => {
    return request.get<StudyCheckinVO[]>('/study-checkins/me')
  },
  delete: (id: number) => {
    return request.delete(`/study-checkins/${id}`)
  },
}
