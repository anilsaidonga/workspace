import { useState } from 'react'
import ControlledComponent from './ControlledComponent'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <ControlledComponent />
      </div>
    </>
  )
}

export default App
