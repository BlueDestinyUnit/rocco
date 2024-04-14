const dialog = document.getElementById('dialog');
const elementsWithIndex = document.querySelectorAll('[data-index]');
let currentIndex = 1;

findDataIndex()




const customerRegisForm = document.getElementById('customerRegisForm');

const paymentRegisForm = document.getElementById('paymentRegisForm');

customerRegisForm.onsubmit = function () {
    
}



function findDataIndex() {
    Array.from(elementsWithIndex).forEach(element => {
        console.log(element)
        const dataIndex = element.getAttribute('data-index');
        console.log("dataIndex:"+  dataIndex);

        if(currentIndex === parseInt(dataIndex)){
            element.block();
        }else{
            element.none();
        }
    });
}




// NodeList를 배열로 변환하여 반복






