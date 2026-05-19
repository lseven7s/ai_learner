import { Tag } from 'antd'
import type { ReactNode } from 'react'

/** 与数据库一致：1-进行中，0-已完成，2-已取消 */
export const PLAN_STATUS = {
  IN_PROGRESS: 1,
  COMPLETED: 0,
  CANCELLED: 2,
} as const

export function getPlanStatusTag(status: number): ReactNode {
  switch (status) {
    case PLAN_STATUS.IN_PROGRESS:
      return <Tag color="blue">进行中</Tag>
    case PLAN_STATUS.COMPLETED:
      return <Tag color="green">已完成</Tag>
    case PLAN_STATUS.CANCELLED:
      return <Tag color="red">已取消</Tag>
    default:
      return <Tag>未知</Tag>
  }
}

export function isPlanInProgress(status: number): boolean {
  return status === PLAN_STATUS.IN_PROGRESS
}
