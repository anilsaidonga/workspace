import { useState } from 'react';

function Login()
{
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const list = [{id: 1, value: 'apple'},
                 {id: 2, value: 'banana'},
                 {id: 3, value: 'custard apple'},
                 {id: 4, value: 'dragon fruit'}
    ];

    function logout()
    {
        setIsLoggedIn(false);
    }

    function login()
    {
        setIsLoggedIn(true)
    }
    
    if (isLoggedIn)
    {
        return(
            <div>
                <h1>Welcome back</h1>
                <ul>
                    {list.map(function(item)
                    {
                        return(<li key={item.id}>{item.value}</li>);
                    })}
                </ul>
                <button onClick={logout}>logout</button>
            </div>
        );
    }
    else
    {
        return(
            <div>
                <h1>Please Login in</h1>
                <button onClick={login}>login</button>
            </div>
        );
    }
}

export default Login;