import { useState } from 'react'
import Header from './Header.jsx'
import Body from './Body.jsx'
import Footer from './Footer.jsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <Header />
        <Body />
        <Footer />
      </div>
    </>
  )
}

export default App
