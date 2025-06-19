import { useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div className="min-h-screen bg-white flex flex-col">
        {/* NavBar */}
        <header className="flex justify-between items-center px-8 py-4 shadow-md">
          <div className="flex items-center space-x-6">
            <nav className="hidden md:flex space-x-6 text-sm font-semibold">
              <a href="#">HOME</a>
              <a href="#">MY BOOKS</a>
              <a href="#">TESTIMONIALS</a>
              <a href="#">OUR STORY</a>
              <img src="/logo.png" alt="Logo" className="h-8" />
              <a href="#">FAQ'S</a>
            </nav>
          </div>
          <div className="flex items-center space-x-4">
            <button className="text-sm">🇺🇸 US</button>
            <button className="px-4 py-2 border rounded-full text-sm">Sign Up</button>
            <button className="px-4 py-2 bg-blue-600 text-white rounded-full text-sm">Log In</button>
          </div>
        </header>

        {/* Main Content */}
        <main className="flex flex-1">
          {/* Left Section */}
          <div className="w-1/2 bg-blue-100 flex justify-center items-center p-10">
            <div className="w-3/4 h-3/4 bg-blue-200 flex items-center justify-center rounded-xl">
              <span className="text-blue-400">[Image/Slider]</span>
            </div>
          </div>

          {/* Right Section */}
          <div className="w-1/2 flex justify-center items-center p-10">
            <div className="bg-white shadow-lg rounded-xl p-8 w-full max-w-md space-y-6">
              <div className="flex flex-col items-center">
                <div className="mb-2">
                  <img src="/login-icon.png" alt="Login Icon" className="h-12" />
                </div>
                <h2 className="text-2xl font-bold">Log In</h2>
                <p className="text-center text-sm text-gray-600 mt-2">
                  Welcome to the magical world! ✨🌌 <br />
                  Step into a place where imagination comes alive.
                </p>
              </div>

              <div className="space-y-4">
                <button className="w-full flex items-center justify-center space-x-2 px-4 py-2 rounded-full bg-gray-100 hover:bg-gray-200">
                  <img src="/google-icon.svg" className="h-5" alt="Google" />
                  <span>Continue with Google</span>
                </button>
                <button className="w-full flex items-center justify-center space-x-2 px-4 py-2 rounded-full bg-gray-100 hover:bg-gray-200">
                  <img src="/facebook-icon.svg" className="h-5" alt="Facebook" />
                  <span>Continue with Facebook</span>
                </button>
                <button className="w-full flex items-center justify-center space-x-2 px-4 py-2 rounded-full bg-gray-100 hover:bg-gray-200">
                  <img src="/apple-icon.svg" className="h-5" alt="Apple" />
                  <span>Continue with Apple</span>
                </button>
                <button className="w-full flex items-center justify-center space-x-2 px-4 py-2 rounded-full bg-gray-100 hover:bg-gray-200">
                  <img src="/email-icon.svg" className="h-5" alt="Email" />
                  <span>Sign in with Email</span>
                </button>
              </div>

              <p className="text-center text-sm text-gray-600">
                Don’t have an account?{" "}
                <a href="#" className="text-blue-600 font-semibold">Sign Up</a>
              </p>
            </div>
          </div>
        </main>
      </div>
    </>
  )
}

export default App
