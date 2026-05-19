export const formatDate = (date: Date): string => {
  return date.toLocaleDateString('zh-CN')
}

export const classNames = (...classes: (string | undefined | null | false)[]): string => {
  return classes.filter(Boolean).join(' ')
}
