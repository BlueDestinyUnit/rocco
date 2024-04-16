const paymentDialog = document.getElementById('paymentDialog');
const elementsWithIndex = document.querySelectorAll('[data-index]');
const paymentReady = document.querySelector('[rel="paymentReady"]');

// form
const customerRegisForm = document.getElementById('customerRegisForm');
const paymentRegisForm = document.getElementById('paymentRegisForm');

// 버튼
const returnButtons = document.querySelectorAll('[rel="returnButton"]');
const cancleButton =document.querySelector('[rel="cancleButton"]');
const nextButton = document.querySelector('[rel="nextButton"]');

// 다이아로그 인덱스
let currentIndex = 1;

// 뒤로 가기 버튼
for (let i = 0; i < returnButtons.length; i++) {
    returnButtons[i].addEventListener('click', (e) => {
       currentIndex -=1;
       findDataIndex();
    });
}

// 다이아로그를 여는 함수
paymentReady.onclick = function (e) {
    cover.show();
    paymentDialog.show();
    findDataIndex();
}

// 취소 버튼
cancleButton.onclick = function (e) {
    e.preventDefault();
    currentIndex = 1;
    cover.hide();
    paymentDialog.hide();
}

nextButton.onclick = function (e) {
    e.preventDefault();
    currentIndex += 1;
    findDataIndex();
}

// 고객 정보 입력
customerRegisForm.onsubmit = function (e) {
    e.preventDefault();
    currentIndex = currentIndex + 1;
    findDataIndex();
}

// 결제 입력
paymentRegisForm.onsubmit = function (e) {
    e.preventDefault();
    currentIndex = 1;
    cover.hide();
    paymentDialog.hide();
}


// 다이아로그 순서를 정하는 함수
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








