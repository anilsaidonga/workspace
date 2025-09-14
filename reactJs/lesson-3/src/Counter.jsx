import { useState } from "react";

function Counter({name})
{
    const [count, setCount] = useState(0);

    return (
        <div>
            <h1>{name}'s Counter!</h1>
            <h1>{count}</h1>
            <button onClick={() => setCount(count + 1)}>increment</button>
            <button onClick={() => setCount(count - 1)}>decrement</button>
            <button onClick={() => setCount(0)}>reset</button>
        </div>
    );
}

export default Counter;