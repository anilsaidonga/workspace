import { useState } from 'react';

function ControlledComponent()
{
    const [text, setText] = useState("");

    function formSubmitted()
    {
        alert("form submitted!");
    }

    function changed(e)
    {
        setText(e.target.value);
    }
    
    return (
        <div>
            <form onSubmit={formSubmitted}>
                <input type="text" value={text} onChange={changed}></input>
                <button type='submit'>Submit</button>
            </form>
        </div>
    );
}

export default ControlledComponent;