const dialog = document.getElementById('dialog');
const elementsWithIndex = document.querySelectorAll('[data-index]');
const paymentReady = document.getElementById('paymentReady');

let currentIndex = 1;

const customerRegisForm = document.getElementById('customerRegisForm');

const paymentRegisForm = document.getElementById('paymentRegisForm');

customerRegisForm.onsubmit = function (e) {
    e.preventDefault();
    currentIndex = currentIndex + 1;
    findDataIndex();
}

paymentRegisForm.onsubmit = function (e) {
    e.preventDefault();
    currentIndex = 1;
    cover.hide();
    dialog.hide();
}

paymentReady.onclick = function (e) {
    cover.show();
    dialog.show();
    findDataIndex();

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








