import axios from 'axios'
import { message } from 'antd'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 30000,
})

request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code !== 200) {
      message.error(res.message || '请求失败')
      if (res.code === 401) {
        localStorage.removeItem('token')
        localStorage.removeItem('user-storage')
        window.location.href = '/login'
      }
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  (error) => {
    const msg =
      error.response?.data?.message || error.message || '网络错误'
    message.error(msg)
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user-storage')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default request
