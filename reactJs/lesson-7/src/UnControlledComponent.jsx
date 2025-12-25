import { useRef } from 'react';

function UnControlledComponent()
{
    const text = useRef();

    function submitted()
    {
        alert("form submitted!");
    }

    return (
        <div>
            <form onSubmit={submitted}>
                <input type="text" ref={changed}></input>
                <button type='submit'>Submit</button>
            </form>
        </div>
    );
}