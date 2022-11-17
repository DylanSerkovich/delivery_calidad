const imagenes = document.querySelectorAll('img');
const btnTodos = document.querySelector('.todos');
const btnFondos = document.querySelector('.fondos');
const btnMariscos = document.querySelector('.mariscos');
const btnRefrescos = document.querySelector('.refrescos');
const btnCervezas = document.querySelector('.cervezas');
const contenedorPlatillos = document.querySelector('.platillos');

document.addEventListener('DOMContentLoaded',()=>{
    platillos();
});

/*imagenes para mostrar*/
const observer = new IntersectionObserver((entries, observer)=>{
    entries.forEach(entry=>{
        if(entry.isIntersecting){
            const imagen = entry.target;
            imagen.src = imagen.dataset.src;
            observer.unobserve(imagen);
        }
    }); 
});
imagenes.forEach(imagen=>{
    observer.observe(imagen);
});

/*funcion para los botones de platillos con arreglos*/
const platillos = () =>{
    let platillosArreglo = [];
    const platillos = document.querySelectorAll('.platillo');
    platillos.forEach(platillo=> platillosArreglo = [...platillosArreglo,platillo]);
    const fondos = platillosArreglo.filter(fondo=> fondo.getAttribute('data-platillo') === 'Fondos');
    const mariscos = platillosArreglo.filter(marisco => marisco.getAttribute('data-platillo') === 'Mariscos');
    const refrescos = platillosArreglo.filter(refresco => refresco.getAttribute('data-platillo') === 'Refrescos');
    const cervezas = platillosArreglo.filter(cerveza=> cerveza.getAttribute('data-platillo') === 'Cervezas');

    mostrarPlatillos(fondos, mariscos, refrescos, cervezas, platillosArreglo);
}

const mostrarPlatillos = (fondos, mariscos, refrescos, cervezas, todos) =>{
    btnFondos.addEventListener('click', ()=>{
        limpiarHtml(contenedorPlatillos);
        fondos.forEach(fondo=> contenedorPlatillos.appendChild(fondo));
    });

    btnMariscos.addEventListener('click', ()=>{
        limpiarHtml(contenedorPlatillos);
        mariscos.forEach(marisco=> contenedorPlatillos.appendChild(marisco));
    });

    btnRefrescos.addEventListener('click', ()=>{
        limpiarHtml(contenedorPlatillos);
        refrescos.forEach(refresco=> contenedorPlatillos.appendChild(refresco));
    });
    btnCervezas.addEventListener('click', ()=>{
        limpiarHtml(contenedorPlatillos);
        cervezas.forEach(cerveza=> contenedorPlatillos.appendChild(cerveza));
    });
    btnTodos.addEventListener('click',()=>{
        limpiarHtml(contenedorPlatillos);
        todos.forEach(todo=> contenedorPlatillos.appendChild(todo));
    });
}

const limpiarHtml = (contenedor) =>{
    while(contenedor.firstChild){
        contenedor.removeChild(contenedor.firstChild);
    }
}