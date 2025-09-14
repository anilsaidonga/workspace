import { useState } from 'react';

function ControlledComponent()
{
    const [text, setText] = useState("");

    function submit(event)
    {
        alert("form submitted!");
    }

    function changed(event)
    {
        setText(event.target.value);
    }

    return (
        <div>
            <form onSubmit={submit}>
                <input type="text" value={text} onChange={changed}></input>

                <button type="submit">Submit</button>
            </form>
        </div>
    );
}

export default ControlledComponent;