(async () => {
    const url = 'http://localhost:8080/restaurants';
    const response = await fetch(url);
    const restaurants = await response.json();
    console.log(restaurants);

    const element = document.querySelector("#app");
    element.innerHTML = `
        ${restaurants.map(restaurant=>`
            <p>
                ${restaurant.id}
                ${restaurant.name}
                ${restaurant.addr}
            </p>
        `).join('')}
    `;


    // restaurants.forEach(restaurant=>{
    //     element.innerHTML += `
    //         <p>
    //         ${restaurant.id}
    //         ${restaurant.name}
    //         ${restaurant.addr}
    //         </p>
    //         `
    // })

})();