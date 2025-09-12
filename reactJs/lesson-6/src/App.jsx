import { useState } from 'react'
import ConditionalRendering from './ConditionalRendering'
import Login from './Login'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <Login />
        <ConditionalRendering isLoggedIn={true}></ConditionalRendering>
      </div>
    </>
  )
}

export default App
