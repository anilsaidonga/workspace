import { useState } from 'react'
import Counter from './Counter.jsx'

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <div>
        <Counter name="Anil Sai Donga"/>
        <Counter name="Sandy" />
        <Counter name="Maesh" />
        <Counter name="blah" />
      </div>
    </>
  )
}

export default App
