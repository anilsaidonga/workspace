function List()
{
    const list = [{id: 1, value: 'apple'},
                 {id: 2, value: 'banana'},
                 {id: 3, value: 'custard apple'},
                 {id: 4, value: 'dragon fruit'}
    ]

    return(
        <div>
            <ul>
                {
                    list.map(function(item)
                    {
                        return (<li key={item.id}>{item.value}</li>);
                    }
                    )
                }
            </ul>
        </div>
    );
}

export default List;