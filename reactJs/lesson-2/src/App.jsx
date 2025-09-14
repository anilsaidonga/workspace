import { useState } from 'react'

import Greeting from './Greeting.jsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div>
      <Greeting name="Anil Sai Donga" />
      <Greeting name="Pradeep" />
      <Greeting name="Sandy" />
    </div>
  );
}

export default App
