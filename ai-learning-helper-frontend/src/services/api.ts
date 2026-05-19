import request from '../utils/request'

export interface UserVO {
  id: number
  username: string
  nickname: string
  email?: string
  avatar?: string
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
  fileUrl: string
  fileType: string
  fileSize: number
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
  content?: string
  createTime: string
}

export const userApi = {
  login: (data: { username: string; password: string }) => {
    return request.post<LoginResponseVO>('/user/login', data)
  },
  register: (data: { username: string; password: string; nickname?: string }) => {
    return request.post('/user/register', data)
  },
  getCurrentUser: () => {
    return request.get<UserVO>('/user/current')
  },
  updateUser: (data: { nickname?: string; email?: string; avatar?: string }) => {
    return request.put('/user/update', data)
  },
}

export const materialApi = {
  upload: (formData: FormData) => {
    return request.post<StudyMaterialVO>('/material/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
  },
  list: (params?: { keyword?: string; fileType?: string }) => {
    return request.get<StudyMaterialVO[]>('/material/list', { params })
  },
  delete: (id: number) => {
    return request.delete(`/material/${id}`)
  },
}

export const planApi = {
  create: (data: { title: string; description?: string; startDate: string; endDate: string }) => {
    return request.post<StudyPlanVO>('/plan/create', data)
  },
  list: () => {
    return request.get<StudyPlanVO[]>('/plan/list')
  },
  update: (id: number, data: { title?: string; description?: string; startDate?: string; endDate?: string; status?: number }) => {
    return request.put(`/plan/${id}`, data)
  },
  delete: (id: number) => {
    return request.delete(`/plan/${id}`)
  },
  generate: (data: { topic: string; duration: number }) => {
    return request.post<StudyPlanVO>('/plan/generate', data)
  },
}

export const checkinApi = {
  checkin: (data: { planId: number; content?: string }) => {
    return request.post<StudyCheckinVO>('/checkin', data)
  },
  list: (planId?: number) => {
    return request.get<StudyCheckinVO[]>('/checkin/list', { params: { planId } })
  },
  delete: (id: number) => {
    return request.delete(`/checkin/${id}`)
  },
}
