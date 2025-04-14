import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
      <div>
      <div class="container">
        <div class="header">
          <h2>Files</h2>
          <button class="upload-btn" onclick="uploadFile()">+</button>
        </div>

        <div class="file-list">
          <div class="file-item">📄 File 1</div>
          <div class="file-item">📄 File 2</div>
          <div class="file-item">📄 File 3</div>
        </div>
      </div>

      </div>
  )
}

export default App
