const trTabla = $("#tabla tbody>tr")
const tr = $("#lista tbody>tr")
const td = $("#lista tbody>tr>td")
function buscar(){
    const input = $("#Search");
    const valor = input.val()
    const table = $("#tabla")
    const tdTabla = $("#tabla tbody>tr>td")
    const trTablafun = $("#tabla tbody>tr")

    let elementosEncontrados = []
    for (let elemento of trTablafun){
        if (elemento.parentNode !== null){
            elemento.parentNode.removeChild(elemento)
        }
    }
    if(valor !== ""){
        for (let elemento of td){
            if (elemento.id === "nombre"){
                if (elemento.innerText.toLowerCase().includes(valor.toLowerCase())){
                    elementosEncontrados.push(elemento.parentElement)
                }
                else {
                    for (let elementoTabla of tdTabla){
                        if (elemento === elementoTabla){
                            if (elemento.parentNode.parentNode !== null) {
                                elemento.parentNode.parentNode.removeChild(elemento.parentNode)
                            }
                        }
                    }
                }
            }
        }
        for (let elemento of elementosEncontrados){
            table.append(elemento)
        }
    } else {
        for (let elemento of trTablafun){
            if (elemento.parentNode !== null){
                elemento.parentNode.removeChild(elemento)
            }
        }
        for (let elemento of trTabla){
            table.append(elemento)
        }
    }
}

