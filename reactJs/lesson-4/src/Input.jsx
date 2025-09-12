import { useState } from "react"

function Input()
{
    const [text, setText] = useState("")

    function changed(event)
    {
        setText(event.target.value);
    }

    function reset()
    {
        setText("");
    }
    
    return(
        <div>
            <input type="text" value={text} onChange={changed}></input>
            <p>text: {text}</p>
            <button onClick={reset}>reset</button>
        </div>
    );
}

export default Input;