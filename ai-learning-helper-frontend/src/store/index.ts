import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import type { UserVO } from '../services/api'

interface UserStore {
  token: string | null
  userInfo: UserVO | null
  setToken: (token: string) => void
  setUserInfo: (userInfo: UserVO) => void
  logout: () => void
}

export const useUserStore = create<UserStore>()(
  persist(
    (set) => ({
      token: null,
      userInfo: null,
      setToken: (token) => {
        localStorage.setItem('token', token)
        set({ token })
      },
      setUserInfo: (userInfo) => set({ userInfo }),
      logout: () => {
        localStorage.removeItem('token')
        set({ token: null, userInfo: null })
      },
    }),
    {
      name: 'user-storage',
      onRehydrateStorage: () => (state) => {
        if (state?.token) {
          localStorage.setItem('token', state.token)
        }
      },
    }
  )
)
