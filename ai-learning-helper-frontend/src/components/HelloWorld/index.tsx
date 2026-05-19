import { Typography } from 'antd'
import React from 'react'

const { Text } = Typography

interface HelloWorldProps {
  name?: string
}

const HelloWorld: React.FC<HelloWorldProps> = ({ name = 'World' }) => {
  return <Text strong>Hello, {name}!</Text>
}

export default HelloWorld
