import { createBrowserRouter, Navigate } from 'react-router-dom'
import App from '../App'
import Login from '../pages/Login'
import Home from '../pages/Home'
import MaterialManage from '../pages/MaterialManage'
import StudyPlan from '../pages/StudyPlan'
import Profile from '../pages/Profile'
import { useUserStore } from '../store'

const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { token } = useUserStore()
  if (!token) {
    return <Navigate to="/login" replace />
  }
  return <>{children}</>
}

const router = createBrowserRouter([
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/',
    element: (
      <ProtectedRoute>
        <App />
      </ProtectedRoute>
    ),
    children: [
      {
        path: '',
        element: <Navigate to="/home" replace />,
      },
      {
        path: 'home',
        element: <Home />,
      },
      {
        path: 'materials',
        element: <MaterialManage />,
      },
      {
        path: 'plans',
        element: <StudyPlan />,
      },
      {
        path: 'profile',
        element: <Profile />,
      },
    ],
  },
])

export default router
