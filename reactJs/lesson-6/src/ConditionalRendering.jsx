function ConditionalRendering(props)
{
    if (props.isLoggedIn)
    {
        return (
            <div>
                <h1>Welcome back!</h1>
            </div>
        )
    }
    else
    {
        return (
            <div>
                <h1>Please login</h1>
            </div>
        )
    }
}

export default ConditionalRendering;